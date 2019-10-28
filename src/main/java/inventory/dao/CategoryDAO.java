package inventory.dao;

import java.util.List;

import inventory.model.Category;
import inventory.model.Paging;

public interface CategoryDAO<E> extends BaseDAO<E> {
	List<Category> getAllCategory(Category category, Paging paging);
}
