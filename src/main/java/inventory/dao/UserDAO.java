package inventory.dao;

import java.util.List;

import inventory.model.Paging;

public interface UserDAO<E> extends BaseDAO<E>{
	List<E> getUserList(E user,Paging paging);
}
