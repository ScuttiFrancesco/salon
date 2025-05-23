package com.salon.customer;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salon.person.PersonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto extends PersonDto {
    
    private Long id;   
    private Instant firstAccess;

    public String getNameAndPhone() {
        return getName() + " " + getSurname() + " - " + getPhoneNumber();
    }
}
