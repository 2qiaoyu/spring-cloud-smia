package com.joham.specialroutes.controllers;


import com.joham.specialroutes.model.AbTestingRoute;
import com.joham.specialroutes.services.AbTestingRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author joham
 */
@RestController
@RequestMapping(value = "v1/route/")
public class SpecialRoutesServiceController {

    @Autowired
    AbTestingRouteService routeService;

    @RequestMapping(value = "abtesting/{serviceName}", method = RequestMethod.GET)
    public AbTestingRoute abstestings(@PathVariable("serviceName") String serviceName) {

        return routeService.getRoute(serviceName);
    }

}
