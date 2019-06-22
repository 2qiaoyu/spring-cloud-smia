package com.joham.organization.services;

import com.joham.organization.events.source.SimpleSourceBean;
import com.joham.organization.model.Organization;
import com.joham.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author joham
 */
@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository orgRepository;

    @Autowired
    private SimpleSourceBean simpleSourceBean;

    public Organization getOrg(String organizationId) {
        return orgRepository.findById(organizationId);
    }

    public void saveOrg(Organization org) {
        org.setId(UUID.randomUUID().toString());
        orgRepository.save(org);
        simpleSourceBean.publishOrgChange("SAVE", org.getId());
    }

    public void updateOrg(Organization org) {
        orgRepository.save(org);
        simpleSourceBean.publishOrgChange("UPDATE", org.getId());
    }

    public void deleteOrg(String organizationId) {
        orgRepository.delete(organizationId);
        simpleSourceBean.publishOrgChange("DELETE", organizationId);
    }
}
