package inventory.service;

import java.util.Date;
import java.util.List;

import javax.script.Invocable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventory.dao.ProductInStockDAO;
import inventory.model.Invoice;
import inventory.model.Paging;
import inventory.model.ProductInStock;
import inventory.model.ProductInfo;

@Service
public class ProductInStockService {
	@Autowired
	private ProductInStockDAO<ProductInStock> productInStockDAO;
	
	private static final Logger log = Logger.getLogger(ProductInStockService.class);
	
	public List<ProductInStock> getAll(ProductInStock productInStock,Paging paging){
		return productInStockDAO.getAll(productInStock, paging);
	}
	
	//Khi ta nhap hang hoa phai save hoa don vao productinstock de cap nhat so luong va gia hien tai
	//neu san pham chua co trong kho ta phai them moi
	public void saveOrUpdate(Invoice invoice) {
		log.info("product in stock");
		if(invoice.getProductInfo()!=null) {
			String code=invoice.getProductInfo().getCode();
			//kiem tra xem kho co ma san pham nay chua
			List<ProductInStock> products = productInStockDAO.findByProperty("productInfo.code", code);
			ProductInStock product=null;
			if(products!=null && !products.isEmpty()) {
				product=products.get(0);
				log.info("update qty="+invoice.getQty()+" and price="+invoice.getPrice());
				product.setQty(product.getQty()+invoice.getQty());
				//type=1 receipt,type=2 issues
				if(invoice.getType()==1) {
					product.setPrice(invoice.getPrice());
				}
				
				product.setUpdateDate(new Date());
				productInStockDAO.update(product);
			}else if(invoice.getType()==1){
				log.info("insert to stock qty="+invoice.getQty()+" and price="+invoice.getPrice());
				product = new ProductInStock();
				ProductInfo productInfo = new ProductInfo();
				productInfo.setId(invoice.getProductInfo().getId());
				product.setProductInfo(productInfo);
				product.setActiveFlag(1);
				product.setCreateDate(new Date());
				product.setUpdateDate(new Date());
				product.setQty(invoice.getQty());
				product.setPrice(invoice.getPrice());
				productInStockDAO.save(product);
			}
			
		}
	}
}
