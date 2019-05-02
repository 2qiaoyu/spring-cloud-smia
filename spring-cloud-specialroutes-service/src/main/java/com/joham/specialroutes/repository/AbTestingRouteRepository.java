package com.joham.specialroutes.repository;

import com.joham.specialroutes.model.AbTestingRoute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author joham
 */
@Repository
public interface AbTestingRouteRepository extends CrudRepository<AbTestingRoute, String> {

    AbTestingRoute findByServiceName(String serviceName);
}
