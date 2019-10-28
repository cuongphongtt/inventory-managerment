package inventory.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.Paging;
import inventory.model.ProductInStock;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInStockDAOimpl extends BaseDAOimpl<ProductInStock> implements ProductInStockDAO<ProductInStock> {
	private static final Logger log = Logger.getLogger(ProductInStockDAOimpl.class);
	@Override
	public List<ProductInStock> getAll(ProductInStock productInStock, Paging paging) {
		log.info("show all product in stock");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if (productInStock != null && productInStock.getProductInfo() != null) {
			if (StringUtils.isNotBlank(productInStock.getProductInfo().getName())) {
				queryStr.append(" and model.productInfo.category.name like : cateName");
				mapParams.put("cateName","%"+ productInStock.getProductInfo().getCategory().getName()+"%");
			}
			if (StringUtils.isNotBlank(productInStock.getProductInfo().getName())) {
				queryStr.append(" and model.productInfo.name like : name");
				mapParams.put("name","%"+ productInStock.getProductInfo().getName()+"%");
			}
			if (StringUtils.isNotBlank(productInStock.getProductInfo().getCode())) {
				queryStr.append(" and model.productInfo.code = : code");
				mapParams.put("code", productInStock.getProductInfo().getCode());
			}
		}
		return findAll(queryStr.toString(), mapParams, paging);
	}
}
