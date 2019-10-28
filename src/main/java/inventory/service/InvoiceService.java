package inventory.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.pattern.LogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventory.dao.InvoiceDAO;
import inventory.dao.ProductInStockDAO;
import inventory.model.Invoice;
import inventory.model.Paging;
import inventory.model.ProductInfo;
import inventory.util.Constant;

@Service
public class InvoiceService {

	@Autowired
	private ProductInStockService productInStockService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private InvoiceDAO<Invoice> invoiceDAO;
	
	private static Logger log=Logger.getLogger(InvoiceService.class);
	
	public void save(Invoice invoice) {
		ProductInfo productInfo= new ProductInfo();
		productInfo.setId(invoice.getProductId());
		invoice.setProductInfo(productInfo);
		invoice.setActiveFlag(1);
		invoice.setCreateDate(new Date());
		invoice.setUpdateDate(new Date());
		invoiceDAO.save(invoice);
		historyService.save(invoice, Constant.ACTION_ADD);
		productInStockService.saveOrUpdate(invoice);
	}		
	
	public void update(Invoice invoice) {
		int originQty =invoiceDAO.findById(Invoice.class,invoice.getId()).getQty();
		ProductInfo productInfo = new ProductInfo();
		productInfo.setId(invoice.getProductId());
		invoice.setProductInfo(productInfo);
		invoice.setUpdateDate(new Date());
		Invoice invoice2 = new Invoice();
		invoice2.setProductInfo(invoice.getProductInfo());
		invoice2.setQty(invoice.getQty()-originQty);
		invoice2.setPrice(invoice.getPrice());
		invoiceDAO.update(invoice);
		historyService.save(invoice, Constant.ACTION_EDIT);
		productInStockService.saveOrUpdate(invoice2);
	}
	
	public List<Invoice> find(String property,Object value){
		return invoiceDAO.findByProperty(property, value);
	}
	
	public List<Invoice> getList(Invoice invoice,Paging paging){
		return invoiceDAO.getList(invoice, paging);
	}
}
