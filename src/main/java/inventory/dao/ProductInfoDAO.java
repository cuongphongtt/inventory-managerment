package inventory.dao;

import java.util.List;

import inventory.model.Paging;
import inventory.model.ProductInfo;

public interface ProductInfoDAO<E> extends BaseDAO<E> {
	List<ProductInfo> getAllProductInfo(ProductInfo productInfo, Paging paging);
}
