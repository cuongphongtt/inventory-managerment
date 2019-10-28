package inventory.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.Category;
import inventory.model.Paging;
import inventory.service.ProductService;

@Repository
@Transactional(rollbackFor = Exception.class)
public class CategoryDAOImpl extends BaseDAOimpl<Category> implements CategoryDAO<Category> {
	private static final Logger log = Logger.getLogger(CategoryDAOImpl.class);
	@Override
	public List<Category> getAllCategory(Category category, Paging paging) {
		log.info("show all category");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<>();
		if (category != null) {
			if (category.getId() != null && category.getId() != 0) {
				queryStr.append(" and model.id=:id");
				mapParams.put("id", category.getId());
			}
			if (category.getCode() != null && !StringUtils.isEmpty(category.getCode())) {
				queryStr.append(" and model.code=:code");
				mapParams.put("code", category.getCode());
			}
			if (category.getName() != null && !StringUtils.isEmpty(category.getName())) {
				queryStr.append(" and model.name like:name");
				mapParams.put("name", "%" + category.getName() + "%");
			}
		}

		return findAll(queryStr.toString(), mapParams, paging);
	}
}
