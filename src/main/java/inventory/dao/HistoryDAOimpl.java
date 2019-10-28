package inventory.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.History;
import inventory.model.Paging;

@Repository
@Transactional(rollbackFor = Exception.class)
public class HistoryDAOimpl extends BaseDAOimpl<History> implements HistoryDAO<History> {
	private static final Logger log = Logger.getLogger(HistoryDAOimpl.class);
	@Override
	public List<History> getAll(History history, Paging paging) {
	    log.info("show All History");
	    StringBuilder queryStr = new StringBuilder("");
	    Map<String, Object> mapParams = new HashMap<String, Object>();
	    if(history!=null) {
	    	if(history.getProductInfo()!=null) {
	    		if(history.getProductInfo().getCategory()!=null) {
	    			if(StringUtils.isNotBlank(history.getProductInfo().getCategory().getName())) {
	    				queryStr.append(" and model.productInfo.category.name like:cateName");
	    				mapParams.put("cateName","%"+ history.getProductInfo().getCategory().getName()+"%");
	    			}
	    		}
	    		if(StringUtils.isNotBlank(history.getProductInfo().getCode())) {
	    			queryStr.append(" and model.productInfo.code = :code");
	    			mapParams.put("code", history.getProductInfo().getCode());
	    		}
	    		if(StringUtils.isNotBlank(history.getProductInfo().getName())) {
	    			queryStr.append(" and model.productInfo.name like:name");
	    			mapParams.put("name", history.getProductInfo().getName());
	    		}
	    	}
	    	if(StringUtils.isNotBlank(history.getActionName())) {
	    		queryStr.append(" and model.actionName=:actionName");
	    		mapParams.put("actionName", history.getActionName());
	    	}
	    	if(history.getType()!=0) {
	    		queryStr.append(" and model.type=:type");
	    		mapParams.put("type", history.getType());
	    	}
	    	
	    }
		return findAll(queryStr.toString(), mapParams, paging);
	}

}
