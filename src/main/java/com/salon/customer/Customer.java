package com.salon.customer;

import java.time.Instant;
import java.util.List;

import com.salon.appointment.Appointment;
import com.salon.person.Person;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "customer")
public class Customer extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
    private Instant firstAccess;
    @OneToMany(mappedBy = "customer")
    private List<Appointment> appointments;


    
}
