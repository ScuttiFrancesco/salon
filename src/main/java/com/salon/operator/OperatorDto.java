package com.salon.operator;

import java.time.LocalDate;

import com.salon.person.PersonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OperatorDto extends PersonDto {
    
    private Long id;
    private LocalDate hiringDate;
    private String position;
    private int level;
    
    // Derived property for name and phone concatenation
    public String getNameAndPhone() {
        return getName() + " " + getSurname() + " - " + getPhoneNumber();
    }
}
