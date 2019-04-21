package com.joham.licenses.clients;


import com.joham.licenses.model.Organization;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author joham
 */
@FeignClient("organizationservice")
public interface OrganizationFeignClient {

    /**
     * 调用组织服务
     *
     * @param organizationId
     * @return
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/v1/organizations/{organizationId}",
            consumes = "application/json")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
