package com.joham.licenses.clients;

import com.joham.licenses.model.Organization;
import com.joham.licenses.repository.OrganizationRedisRepository;
import com.joham.licenses.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author joham
 */
@Component
@Slf4j
public class OrganizationRestTemplateClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrganizationRedisRepository organizationRedisRepository;

    private Organization checkRedisCache(String organizationId) {
        try {
            return organizationRedisRepository.findOrganization(organizationId);
        } catch (Exception ex) {
            log.error("Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}", organizationId, ex);
            return null;
        }
    }

    private void cacheOrganizationObject(Organization org) {
        try {
            organizationRedisRepository.saveOrganization(org);
        } catch (Exception ex) {
            log.error("Unable to cache organization {} in Redis. Exception {}", org.getId(), ex);
        }
    }

    public Organization getOrganization(String organizationId) {
        log.info("In Licensing Service.getOrganization: {}", UserContextHolder.getContext().getCorrelationId());

        Organization org = checkRedisCache(organizationId);

        if (org != null) {
            log.info("I have successfully retrieved an organization {} from the redis cache: {}", organizationId, org);
            return org;
        }

        log.info("Unable to locate organization from the redis cache: {}.", organizationId);

        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://localhost:5555/api/organization/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        /*Save the record from cache*/
        org = restExchange.getBody();

        if (org != null) {
            cacheOrganizationObject(org);
        }

        return org;
    }
}
