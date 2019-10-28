package inventory.model;

public class Paging {
	private long totalRows;//Tổng số dòng
	private int totalPages;//Tổng số trang
	private int indexPages;//Trang hiện tại
	private int recordPerPage=10;//Tổng số bản ghi ở trên mỗi 1 trang là 10
	private int offset;//0,10-19,20-29,...
	
	public Paging(int recordPerPage) {
		this.recordPerPage=recordPerPage;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		if(totalRows>0) {
			totalPages=(int) Math.ceil(totalRows/(double)recordPerPage); 
		}
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getIndexPages() {
		return indexPages;
	}

	public void setIndexPages(int indexPages) {
		this.indexPages = indexPages;
	}

	public int getRecordPerPage() {
		return recordPerPage;
	}

	public void setRecordPerPage(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}

	public int getOffset() {
		if (indexPages>0) {
			offset=indexPages*recordPerPage-recordPerPage;//1 *10-10=0;2*10-10=10
		}
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
