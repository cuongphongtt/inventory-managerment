package inventory.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import inventory.model.Auth;
import inventory.model.Menu;
import inventory.model.Role;
import inventory.model.UserRole;
import inventory.model.Users;
import inventory.service.UserService;
import inventory.util.Constant;
import inventory.validate.LoginValidator;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@Autowired
	LoginValidator loginValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null)
			return;
		// Kiểm tra xem form xem có đúng là Users.class không vì nếu không là Users thì
		// không validate được
		if (binder.getTarget().getClass() == Users.class) {
			binder.setValidator(loginValidator);
		}
	}

	
	// Định nghĩa 1 đường dẫn dẫn tới form login
	@GetMapping("/login")
	public String login(Model model) {
		// Model giúp vận chuyển các dữ liệu từ backend tới views thông qua model
		model.addAttribute("loginForm", new Users());// loginForm phải trùng tên với modelAttribute bên form Login
		return "login/login";
	}

	@PostMapping("/processLogin")
	public String processLogin(Model model, @ModelAttribute("loginForm") @Validated Users users, BindingResult result,
			HttpSession session) {
		// Bindingresult là kết quả trả về
		// HttpSession là khi user đăng nhập thành công thì save thông tin user vào
		// session
		if (result.hasErrors()) {
			return "login/login";
		}

		Users user = userService.findByProperty("username", users.getUsername()).get(0);
		// Dựa theo vào user sẽ có được role qua userrole
		// Dựa vào role sẽ lấy được menu qua auth
		// Lấy menu ra và sắp xếp menu cha,con
		UserRole userRole = (UserRole) user.getUserRoles().iterator().next();
		Role role = userRole.getRole();

		List<Menu> menuList = new ArrayList<>();
		List<Menu> menuChildList = new ArrayList<>();
		// Tìm menu cha
		for (Object obj : role.getAuths()) {
			Auth auth = (Auth) obj;// lấy dữ liệu từ hàm Set
			Menu menu = auth.getMenu();
			if (menu.getParentId() == 0 && menu.getOrderIndex() != -1 && menu.getActiveFlag() == 1
					&& auth.getPermission() == 1 && auth.getActiveFlag() == 1) {
				menu.setIdMenu(menu.getUrl().replace("/", "") + "Id");// /category/list =>categorylistId
				menuList.add(menu);
			} else if (menu.getParentId() != 0 && menu.getOrderIndex() != -1 && menu.getActiveFlag() == 1
					&& auth.getPermission() == 1 && auth.getActiveFlag() == 1) {
				menu.setIdMenu(menu.getUrl().replace("/", "") + "Id");
				menuChildList.add(menu);
			}
		}

		for (Menu menu : menuList) {
			List<Menu> childList = new ArrayList<Menu>();
			for (Menu childMenu : menuChildList) {
				if (childMenu.getParentId() == menu.getId()) {
					childList.add(childMenu);
				}
			}
			menu.setChild(childList);
		}
		sortMenu(menuList);
		for (Menu menu : menuList) {
			sortMenu(menu.getChild());
		}
		session.setAttribute(Constant.MENU_SESSION, menuList);
		session.setAttribute(Constant.USER_INFO, user);
		return "redirect:/index";
		// Sau khi đăng nhập thì chuyển vào /index và thông tin user được lưu vào
		// session
	}

	public void sortMenu(List<Menu> menus) {
		Collections.sort(menus, new Comparator<Menu>() {

			public int compare(Menu o1, Menu o2) {
				return o1.getOrderIndex() - o2.getOrderIndex();
			}
		});
	}

}
