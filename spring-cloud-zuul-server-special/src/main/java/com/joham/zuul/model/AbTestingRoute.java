package com.joham.zuul.model;

import lombok.Data;

/**
 * @author joham
 */
@Data
public class AbTestingRoute {

    String serviceName;

    String active;

    String endpoint;

    Integer weight;
}