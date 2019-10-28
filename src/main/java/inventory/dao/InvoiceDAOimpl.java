package inventory.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import inventory.model.Invoice;
import inventory.model.Paging;

@Repository
@Transactional(rollbackFor = Exception.class)
public class InvoiceDAOimpl extends BaseDAOimpl<Invoice> implements InvoiceDAO<Invoice> {
	private Logger log = Logger.getLogger(InvoiceDAOimpl.class);

	@Override
	public List<Invoice> getList(Invoice invoice, Paging paging) {
		log.info("show Invoice");
		StringBuilder queryStr = new StringBuilder("");
		Map<String, Object> mapParams = new HashMap<String, Object>();
		if (invoice != null) {
			if (invoice.getType() != 0) {
				queryStr.append(" and model.type= :type");
				mapParams.put("type", invoice.getType());
			}
			if(StringUtils.isNotBlank(invoice.getCode())) {
				queryStr.append(" and model.code=:code");
				mapParams.put("code", invoice.getCode());
			}
			if(invoice.getFromDate()!=null) {
				queryStr.append(" and model.updateDate>=:fromDate");
				mapParams.put("fromDate", invoice.getFromDate());
			}
			if(invoice.getToDate()!=null) {
				queryStr.append(" and model.updateDate<=:toDate");
				mapParams.put("toDate", invoice.getToDate());
			}
		}
		return findAll(queryStr.toString(), mapParams, paging);
	}

}
