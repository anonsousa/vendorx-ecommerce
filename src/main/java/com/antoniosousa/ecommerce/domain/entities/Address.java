package com.antoniosousa.ecommerce.domain.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {

    private String street;
    private String number;
    private String city;
    private String state;
    private String zip;
}
