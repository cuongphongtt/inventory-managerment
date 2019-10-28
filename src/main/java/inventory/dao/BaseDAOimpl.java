package inventory.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.velocity.runtime.directive.Foreach;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.Paging;

@Repository
@Transactional(rollbackFor = Exception.class) // Nếu có bất kì 1 Exeption nào sẽ rollback lại dữ liệu để đảm bảo// transaction toàn vẹn và nhất quán								
public class BaseDAOimpl<E> implements BaseDAO<E> {
	final static Logger log = Logger.getLogger(BaseDAOimpl.class);
	@Autowired
	SessionFactory sessionFactory;

	public List<E> findAll(String queryStr, Map<String, Object> mapParams, Paging paging) {
		log.info("find all record from db");

		StringBuilder queryString = new StringBuilder("");
		queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1");
		StringBuilder countQuery = new StringBuilder("");
		countQuery.append(" select count(*) from ").append(getGenericName())
				.append(" as model where model.activeFlag=1");
		if (queryStr != null && !queryStr.isEmpty()) {
			queryString.append(queryStr);
			countQuery.append(queryStr);
		}
		Query<E> query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		Query<E> countQ = sessionFactory.getCurrentSession().createQuery(countQuery.toString());
		if (mapParams != null && !mapParams.isEmpty()) {
			for (String key : mapParams.keySet()) {
				query.setParameter(key, mapParams.get(key));
				countQ.setParameter(key, mapParams.get(key));
			}

		}
		if (paging != null) {
			query.setFirstResult(paging.getOffset());// from model where model.activeFlag=1 limit 0,10
			query.setMaxResults(paging.getRecordPerPage());
			long totalRecords = (long) countQ.uniqueResult();// test for null here if needed
			paging.setTotalRows(totalRecords);
		}

		log.info("query find all===>" + queryString.toString());
		return query.list();
	}

	public E findById(Class<E> e, Serializable id) {
		log.info("Find by id");
		return sessionFactory.getCurrentSession().get(e, id);
	}

	public List<E> findByProperty(String property, Object value) {
		log.info("Find by property");
		StringBuilder queryString = new StringBuilder("");
		queryString.append(" from ").append(getGenericName()).append(" as model where model.activeFlag=1 and model.")
				.append(property).append("=?");
		log.info("query find by property===>" + queryString.toString());
		Query<E> query = sessionFactory.getCurrentSession().createQuery(queryString.toString());
		query.setParameter(0, value);
		return query.getResultList();
		// from auth as model where model.activeflag=1 and model.name=?
	}

	public void save(E instance) {
		log.info("save instance");
		sessionFactory.getCurrentSession().persist(instance);

	}

	public void update(E instance) {
		log.info("update instance");
		sessionFactory.getCurrentSession().merge(instance);
	}

	// get NameGeneric để cho hibernate hiểu được E
	public String getGenericName() {
		String s = getClass().getGenericSuperclass().toString();
		// Dùng partern kiểm tra hợp lệ của class
		// Tạo đối tượng Pattern thông qua method tĩnh.
		Pattern pattern = Pattern.compile("\\<(.*?)\\>");
		// Lấy ra đối tượng Matcher
		Matcher m = pattern.matcher(s);
		String generic = "null";
		// method find() để tìm kiếm các chuỗi con khớp với một biểu thức chính quy
		if (m.find()) {
			generic = m.group(1);
		}
		return generic;
	}

}
