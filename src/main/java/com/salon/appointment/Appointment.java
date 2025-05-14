package com.salon.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.salon.customer.Customer;
import com.salon.enums.Service;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;

@Data
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime time;

    @ManyToOne
    private Customer customer;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Service> service;

}
