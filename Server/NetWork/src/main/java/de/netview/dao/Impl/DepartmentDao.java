package de.netview.dao.Impl;

import org.springframework.stereotype.Repository;

import de.netview.dao.IDepartmentDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Department;
import de.netview.model.Hardware;

@Repository
public class DepartmentDao extends AbstractDao<Department>  implements IDepartmentDao {
	
	@Override
	public void createOrUpdateDepartment(Department department) {
		getSession().saveOrUpdate(department);
	}

}
