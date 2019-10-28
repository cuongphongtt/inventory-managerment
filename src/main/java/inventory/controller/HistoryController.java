package inventory.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import inventory.model.History;
import inventory.model.Paging;
import inventory.service.HistoryService;
import inventory.util.Constant;

@Controller
public class HistoryController {
	public static final Logger log = Logger.getLogger(HistoryController.class);
	@Autowired
	private HistoryService historyService;
	@RequestMapping("/history")
	public String redirect() {
		return "redirect:/history/1";
	}
	@RequestMapping("/history/{page}")
	public String showHistory(Model model,@ModelAttribute("searchForm") History history,@PathVariable("page") int page) {
		Paging paging = new Paging(3);
		paging.setIndexPages(page);
		List<History> histories = historyService.getAll(history, paging);
		model.addAttribute("histories",histories);
		model.addAttribute("pageInfo",paging);
		Map<String, Object> mapType = new HashMap<String, Object>();
		mapType.put(String.valueOf(Constant.TYPE_ALL), "All");
		mapType.put(String.valueOf(Constant.TYPE_GOODS_ISSUES), "Good Issues");
		mapType.put(String.valueOf(Constant.TYPE_GOODS_RECEIPT), "Good Receipt");
		model.addAttribute("mapType", mapType);
		return "history";
	}
}
