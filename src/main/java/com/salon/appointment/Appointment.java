package com.salon.appointment;

import java.time.LocalDateTime;

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
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import lombok.Data;

@Data
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private double duration;
    private String notes;

    @ManyToOne
    private Customer customer;

    @ElementCollection(targetClass = Service.class)
    @CollectionTable(name = "appointment_service", joinColumns = @JoinColumn(name = "appointment_id"))
    @Column(name = "service")
    @Enumerated(EnumType.STRING)
    private List<Service> services;

}
