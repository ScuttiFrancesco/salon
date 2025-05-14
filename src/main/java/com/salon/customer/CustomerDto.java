package com.salon.customer;

import java.time.Instant;

import com.salon.person.PersonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerDto extends PersonDto {
    
    private Long id;   
    private Instant firstAccess;

}
