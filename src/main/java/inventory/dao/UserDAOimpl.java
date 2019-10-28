	package inventory.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import freemarker.template.utility.StringUtil;
import inventory.model.Paging;
import inventory.model.Users;
@Repository
@Transactional(rollbackFor = Exception.class)
public class UserDAOimpl extends BaseDAOimpl<Users> implements UserDAO<Users> {

	@Override
	public List<Users> getUserList(Users user, Paging paging) {
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(user.getName())) {
			queryStr.append(" and model.name like:name");
			mapParams.put("name","%" +user.getName()+"%");
		}
		return findAll(queryStr.toString(), mapParams, paging);
	}

}
