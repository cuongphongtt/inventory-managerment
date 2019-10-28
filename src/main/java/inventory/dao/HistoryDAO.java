package inventory.dao;

import java.util.List;

import inventory.model.History;
import inventory.model.Paging;

public interface HistoryDAO<E> extends BaseDAO<E> {
	List<History> getAll(History history,Paging paging);
}
