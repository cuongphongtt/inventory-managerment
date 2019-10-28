package inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import inventory.dao.ProductInStockDAO;
import inventory.model.Paging;
import inventory.model.ProductInStock;
import inventory.service.ProductInStockService;

@Controller
public class ProductInStockController {
	@Autowired
	private ProductInStockService productInStockService;
	@GetMapping("/product-in-stock/list")
	public String redirect(){
		return "redirect:/product-in-stock/list/1";
	}
	@GetMapping("/product-in-stock/list/{page}")
	public String showAllProductInStock(Model model,@ModelAttribute("searchForm") ProductInStock productInStock,@PathVariable("page") int page) {
		Paging paging = new Paging(3);
		paging.setIndexPages(page);
		List<ProductInStock> products =  productInStockService.getAll(productInStock, paging);
		model.addAttribute("products",products);
		model.addAttribute("pageInfo", paging);
		return "product-in-stock";
	}
}
