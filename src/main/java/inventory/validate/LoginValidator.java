package inventory.validate;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import inventory.model.Users;
import inventory.service.UserService;

@Component
public class LoginValidator implements Validator {
	@Autowired
	private UserService userService;

	// Kiểm tra xem support Class nào
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == Users.class;
	}

	// Thông tin gửi từ form lên được kiểm tra tại đây và "target" là thông tin gửi từ form
	public void validate(Object target, Errors errors) {
		Users user = (Users) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "msg.required");
		if (!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())) {
			List<Users> users = userService.findByProperty("username", user.getUsername());
			if (users != null && !users.isEmpty()) {
				if (!users.get(0).getPassword().equals(user.getPassword())) {
					errors.rejectValue("password", "msg.wrong.password");
				}
			} else {
				errors.rejectValue("username", "msg.wrong.username");
			}
		}

	}

}
