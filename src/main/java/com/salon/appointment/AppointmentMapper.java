package com.salon.appointment;

import org.mapstruct.*;
import com.salon.customer.Customer;
import com.salon.customer.CustomerRepository;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "customer.id", target = "customerId")
    AppointmentDto toDto(Appointment appointment);

    @Mapping(source = "customerId", target = "customer")
    Appointment toEntity(AppointmentDto appointmentDto, @Context CustomerRepository customerRepository);

    default Customer map(Long customerId, @Context CustomerRepository customerRepository) {
        if (customerId == null) return null;
        return customerRepository.findById(customerId).orElse(null);
    }

    default Long map(Customer customer) {
        return customer != null ? customer.getId() : null;
    }
}
