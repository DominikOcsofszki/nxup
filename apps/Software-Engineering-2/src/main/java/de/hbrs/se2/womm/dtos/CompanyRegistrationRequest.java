package de.hbrs.se2.womm.dtos;

import lombok.Data;

@Data
public class CompanyRegistrationRequest extends RegistrationRequest {
    private String name;
}
