package inventory.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventory.dao.HistoryDAO;
import inventory.model.History;
import inventory.model.Invoice;
import inventory.model.Paging;

@Service
public class HistoryService {
	@Autowired
	private HistoryDAO<History> historyDAO;
	
	private Logger log = Logger.getLogger(HistoryService.class);
	public List<History> getAll(History history,Paging paging){
		return historyDAO.getAll(history, paging);
	}
	public void save(Invoice invoice,String action) {
		History history = new History();
		history.setProductInfo(invoice.getProductInfo());
		history.setQty(invoice.getQty());
		history.setType(invoice.getType());
		history.setPrice(invoice.getPrice());
		history.setActiveFlag(1);
		history.setActionName(action);
		history.setCreateDate(new Date());
		history.setUpdateDate(new Date());
		historyDAO.save(history);
	}
}
