package com.joham.licenses.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author joham
 */
@Data
public class Organization implements Serializable {

    String id;

    String name;

    String contactName;

    String contactEmail;

    String contactPhone;
}
