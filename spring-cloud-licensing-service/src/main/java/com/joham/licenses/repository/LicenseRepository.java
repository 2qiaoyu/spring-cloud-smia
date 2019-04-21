package com.joham.licenses.repository;

import com.joham.licenses.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author joham
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {

    /**
     * 根据组织Id查询组织
     *
     * @param organizationId
     * @return
     */
    List<License> findByOrganizationId(String organizationId);

    /**
     * 根据组织Id和许可证Id查询组织
     *
     * @param organizationId
     * @param licenseId
     * @return
     */
    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
