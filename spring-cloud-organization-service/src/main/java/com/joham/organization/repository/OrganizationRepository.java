package com.joham.organization.repository;

import com.joham.organization.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author joham
 */
@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {

    /**
     * 根据Id查询
     * @param organizationId
     * @return
     */
    Organization findById(String organizationId);
}
