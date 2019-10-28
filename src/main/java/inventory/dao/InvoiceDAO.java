package inventory.dao;

import java.util.List;

import inventory.model.Invoice;
import inventory.model.Paging;

public interface InvoiceDAO<E> extends BaseDAO<E>{
	List<Invoice> getList(Invoice invoice, Paging paging);
}
