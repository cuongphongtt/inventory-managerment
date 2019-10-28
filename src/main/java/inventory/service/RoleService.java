package inventory.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventory.dao.RoleDAO;
import inventory.model.Paging;
import inventory.model.Role;
import inventory.model.Users;

@Service
public class RoleService {
	@Autowired
	private RoleDAO<Role> roleDAO;
	
	public List<Role> getRoleList(Role role,Paging paging){
		return roleDAO.findAll(null, null, paging);
	}
	
	public void save(Role role) {
		role.setActiveFlag(1);
		role.setCreateDate(new Date());
		role.setUpdateDate(new Date());
		roleDAO.save(role);
	}
	public void update(Role role) {
		role.setUpdateDate(new Date());
		roleDAO.update(role);
	}
	
	public List<Role> findByProperty(String name,Object value) {
		return roleDAO.findByProperty(name, value);
	}
	public Role findById(int id) {
		return roleDAO.findById(Role.class, id);
	}
	public void delete(Role role) {
		role.setActiveFlag(0);
		role.setUpdateDate(new Date());
		roleDAO.update(role);
	}
}
