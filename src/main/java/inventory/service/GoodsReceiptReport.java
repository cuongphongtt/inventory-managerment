package inventory.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import inventory.model.Invoice;
import inventory.util.Constant;
//Class de xuat ra bao cao ra Excel
public class GoodsReceiptReport extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition","attachment;filename=\"goods-receipt-export.xlsx\"");
		//Các Sheet để phân cách,đầu tiên tạo ra sheet chứa dữ liệu
		Sheet sheet= workbook.createSheet("data");
		//tạo ra dòng
		Row header= sheet.createRow(0);
		header.createCell(0).setCellValue("#");
		header.createCell(1).setCellValue("Code");
		header.createCell(2).setCellValue("Qty");
		header.createCell(3).setCellValue("Price");
		header.createCell(4).setCellValue("Product");
		header.createCell(5).setCellValue("Update Date");
		//Binding data
		List<Invoice> invoices =(List<Invoice>) model.get(Constant.KEYS_GOODS_RECEIPT_REPORT);
		int rownum=1;
		for(Invoice invoice : invoices) {
			Row row = sheet.createRow(rownum++);
			row.createCell(0).setCellValue(rownum-1);
			row.createCell(1).setCellValue(invoice.getCode());
			row.createCell(2).setCellValue(invoice.getQty());
			row.createCell(3).setCellValue(invoice.getPrice().toString());
			row.createCell(4).setCellValue(invoice.getProductInfo().getName());
			row.createCell(5).setCellValue(DateUtil.dateToString(invoice.getUpdateDate()));
		}
		
	}

}
