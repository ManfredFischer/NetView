package de.netview.service;

import de.netview.dao.ComponenteHardwareDao;
import de.netview.servicei.IComponenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mf on 09.06.2016.
 */
@Service("employeeService")
@Transactional
public class ComponenteService implements IComponenteService {

    @Autowired
    private ComponenteHardwareDao componenteHardwareDao;
}
