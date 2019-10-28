package inventory.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import inventory.model.Paging;
import inventory.model.Role;
import inventory.service.RoleService;
import inventory.util.Constant;

@Controller
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/role/list")
	public String redirect() {
		return "redirect:/role/list/1";
	}
	
	@GetMapping("/role/list/{page}")
	public String showRoleList(Model model,@PathVariable("page") int page,HttpSession session) {
		Paging paging = new Paging(3);
		paging.setIndexPages(page);
		List<Role> roles = roleService.getRoleList(null, paging);
		if(session.getAttribute(Constant.MSG_SUCCESS)!=null) {
			model.addAttribute(Constant.MSG_SUCCESS, session.getAttribute(Constant.MSG_SUCCESS));
			session.removeAttribute(Constant.MSG_SUCCESS);
		}
		if(session.getAttribute(Constant.MSG_ERROR)!=null) {
			model.addAttribute(Constant.MSG_ERROR, session.getAttribute(Constant.MSG_ERROR));
			session.removeAttribute(Constant.MSG_ERROR);
		}
		model.addAttribute("pageInfo",paging);
		model.addAttribute("roles",roles);
		return "role-list";
	}
	@GetMapping("/role/add")
	public String add(Model model) {
		model.addAttribute("titlePage","Add Role");
		model.addAttribute("viewOnly",false);
		model.addAttribute("modelForm",new Role());
		return "role-action";
	}
	@GetMapping("/role/edit/{id}")
	public String edit(Model model,@PathVariable("id") int id) {
		Role role= roleService.findById(id);
		if(role!=null) {
			model.addAttribute("titlePage","Edit Role");
			model.addAttribute("viewOnly",false);
			model.addAttribute("modelForm",role);
			return "role-action";
		}
		return "redirect:/role/list";
	}
	@GetMapping("/role/view/{id}")
	public String view(Model model,@PathVariable("id") int id) {
		Role role= roleService.findById(id);
		if(role!=null) {
			model.addAttribute("titlePage","Edit Role");
			model.addAttribute("viewOnly",false);
			model.addAttribute("modelForm",role);
			return "role-action";
		}
		return "redirect:/role/list";
	}
	@PostMapping("/role/save")
	public String save(Model model,@ModelAttribute("modelForm") @Validated Role role,BindingResult result,HttpSession session) {
		if(result.hasErrors()) {
			if(role.getId()!=null && role.getId()!=0) {
				model.addAttribute("titlePage","Edit Role");
			}else {
				model.addAttribute("titlePage","Add Role");
			}
			model.addAttribute("viewOnly",false);
			model.addAttribute("modelForm", role);
			return "role-action";
		}
		
		if(role.getId()!=null && role.getId()!=0) {
			try {
				roleService.update(role);
				session.setAttribute(Constant.MSG_SUCCESS,"Update Success !");
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR,"Update has Error");
			}
		}else {
			try {
				roleService.save(role);
				session.setAttribute(Constant.MSG_SUCCESS, "Insert Success");	
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute(Constant.MSG_ERROR, "Insert has Errors");
			}
		}
		
		return "redirect:/role/list";
	}
	@GetMapping("/role/delete/{id}")
	public String delete(Model model,@PathVariable("id") int id,HttpSession session) {
		Role role = roleService.findById(id);
		if(role.getId()!=0 && role.getId()!=null) {
			try {
				roleService.delete(role);
				session.setAttribute(Constant.MSG_SUCCESS, "Delete Success");
			} catch (Exception e) {
				session.setAttribute(Constant.MSG_ERROR, "Delete has Error");
			}			
		}
		return "redirect:/role/list";
	}
}

