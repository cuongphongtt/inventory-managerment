package inventory.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventory.dao.UserDAO;
import inventory.dao.UserRoleDAO;
import inventory.model.Paging;
import inventory.model.Role;
import inventory.model.UserRole;
import inventory.model.Users;
import inventory.util.HashingPassword;

@Service
public class UserService {
	final static Logger log= Logger.getLogger(UserService.class);
	@Autowired
	private UserDAO<Users> userDAO;
	
	@Autowired
	private UserRoleDAO<UserRole> userRoleDAO;
	
	public List<Users> findByProperty(String property,Object value) {
		log.info("Find user by property start");
		return userDAO.findByProperty(property, value);
	}
	
	public Users findById(Integer id) {
		log.info("Find user by id");
		return userDAO.findById(Users.class, id);
	}
	
	public void save(Users user) {
		user.setActiveFlag(1);
		user.setUpdateDatae(new Date() );
		user.setCreateDate(new Date());
		user.setPassword(HashingPassword.encrypt(user.getPassword()));
		userDAO.save(user);
		UserRole userRole = new UserRole();
		userRole.setUsers(user);
		Role role = new Role();
		role.setId(user.getRoleId());
		userRole.setRole(role);
		userRole.setActiveFlag(1);
		userRole.setCreateDate(new Date());
		userRole.setUpdateDate(new Date());
		userRoleDAO.save(userRole);
	}
	
	public void update(Users users) {
		Users user = findById(users.getId());
		if(user!=null) {
			UserRole userRole = (UserRole) user.getUserRoles().iterator().next();
			Role role = userRole.getRole();
			role.setId(users.getRoleId());
			userRole.setRole(role);
			userRole.setUpdateDate(new Date());
			user.setName(users.getName());
			user.setEmail(users.getEmail());
			user.setUsername(users.getUsername());
			user.setUpdateDatae(new Date());
			userRoleDAO.update(userRole);
		}
		userDAO.update(user);	
	}
	public void deleteUser(Users user) {
		user.setActiveFlag(1);
		user.setUpdateDatae(new Date());
		userDAO.update(user);
	}
	public List<Users>  getUserList(Users user,Paging paging) {
		return userDAO.getUserList(user, paging);
	}
}
