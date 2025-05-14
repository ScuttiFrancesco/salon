package com.salon.person;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PersonDto {

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate birthday;

}
