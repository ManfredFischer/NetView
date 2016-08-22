package de.netview.dao.Impl;

import de.netview.dao.ILocationDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mf on 21.08.2016.
 */
@Repository
public class LocationDao extends AbstractDao<Location> implements ILocationDao {
    @Override
    public Location getLocationById(Long lid) {
        List<Location> locations = getSession().createQuery("FROM Location l where  l.lid = :lid").setParameter("lid", lid).list();
        if (locations != null){
            if (!locations.isEmpty()){
                return locations.get(0);
            }
        }
        return null;
    }

    @Override
    public List<Location> getLocation() {
        return getSession().createQuery("FROM Location l where  l.removed = false").list();

    }

    @Override
    public void setLocationToRemovedById(Long lid) {
        String hqlUpdate = "update Location l set l.removed = true where l.lid = :lid";
        getSession().createQuery( hqlUpdate )
                .setParameter( "lid", lid );
    }
}
