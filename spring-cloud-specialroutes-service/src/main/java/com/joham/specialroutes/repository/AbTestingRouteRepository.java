package com.joham.specialroutes.repository;

import com.joham.specialroutes.model.AbTestingRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author joham
 */
@Repository
public interface AbTestingRouteRepository extends CrudRepository<AbTestingRoute, String> {

    /**
     * 查询方法名
     * @param serviceName
     * @return
     */
    AbTestingRoute findByServiceName(String serviceName);
}
