package com.joham.organization.events.models;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author joham
 */
@Data
@AllArgsConstructor
public class OrganizationChangeModel {
    private String type;
    private String action;
    private String organizationId;
    private String correlationId;
}