package com.salon.appointment;

import org.mapstruct.*;
import com.salon.customer.Customer;
import com.salon.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AppointmentMapper {

    @Autowired
    protected CustomerRepository customerRepository;

    @Mapping(source = "customer.id", target = "customerId")
    public abstract AppointmentDto toDto(Appointment appointment);

    @Mapping(source = "customerId", target = "customer")
    public abstract Appointment toEntity(AppointmentDto appointmentDto);

    protected Customer map(Long customerId) {
        if (customerId == null) return null;
        return customerRepository.findById(customerId).orElse(null);
    }

    protected Long map(Customer customer) {
        return customer != null ? customer.getId() : null;
    }
}
