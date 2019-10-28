package inventory.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.Paging;
import inventory.model.ProductInfo;
import inventory.service.ProductService;

@Repository
@Transactional(rollbackFor = Exception.class)
public class ProductInfoDAOimpl extends BaseDAOimpl<ProductInfo> implements ProductInfoDAO<ProductInfo> {
	private static final Logger log = Logger.getLogger(ProductInfoDAOimpl.class);
	public List<ProductInfo> getAllProductInfo(ProductInfo productInfo, Paging paging) {
		log.info("show all productInfo");
		StringBuilder queryStr = new StringBuilder();
		Map<String, Object> mapParams = new HashMap<>();
		if (productInfo != null) {
			if (productInfo.getId() != null && productInfo.getId() != 0) {
				queryStr.append(" and model.id=:id");
				mapParams.put("id", productInfo.getId());
			}
			if (productInfo.getCode() != null && !StringUtils.isEmpty(productInfo.getCode())) {
				queryStr.append(" and model.code=:code");
				mapParams.put("code", productInfo.getCode());
			}
			if (productInfo.getName() != null && !StringUtils.isEmpty(productInfo.getName())) {
				queryStr.append(" and model.name like :name");
				mapParams.put("name", "%" + productInfo.getName() + "%");
			}
		}
		return findAll(queryStr.toString(), mapParams, paging);
	}
}
