package inventory.dao;

import java.util.List;

import inventory.model.Paging;
import inventory.model.ProductInStock;

public interface ProductInStockDAO<E> extends BaseDAO<E> {
	List<ProductInStock> getAll(ProductInStock productInStock,Paging paging);
}
