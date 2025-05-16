package com.salon.person;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public class Person {

    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String address;
    private LocalDate birthdate;

}
