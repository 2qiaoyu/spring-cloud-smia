package com.joham.licenses.clients;

import com.joham.licenses.model.Organization;
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

    public Organization getOrganization(String organizationId) {
        log.info("In Licensing Service.getOrganization: {}", UserContextHolder.getContext().getCorrelationId());

        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        "http://localhost:5555/api/organization/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
