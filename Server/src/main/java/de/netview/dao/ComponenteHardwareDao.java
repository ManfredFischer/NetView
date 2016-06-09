package de.netview.dao;

/**
 * Created by mf on 09.06.2016.
 */

import java.util.List;

import de.netview.daoi.IComponenteHardwareDao;
import de.netview.model.Hardware;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository("ComponenteDao")
public class ComponenteHardwareDao extends AbstractDao<Integer,Hardware> implements IComponenteHardwareDao {

}
