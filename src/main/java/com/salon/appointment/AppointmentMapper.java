package com.salon.appointment;

import org.mapstruct.*;
import com.salon.customer.Customer;
import com.salon.customer.CustomerRepository;
import com.salon.operator.Operator;
import com.salon.operator.OperatorRepository;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "operator.id", target = "operatorId")
    @Mapping(target = "customerNameAndPhone", expression = "java(mapCustomerNameAndPhone(appointment.getCustomer()))")
    @Mapping(target = "operatorNameAndPhone", expression = "java(mapOperatorNameAndPhone(appointment.getOperator()))")
    AppointmentDto toDto(Appointment appointment);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "operatorId", target = "operator")
    Appointment toEntity(AppointmentDto appointmentDto, @Context CustomerRepository customerRepository, @Context OperatorRepository operatorRepository);

    default Customer map(Long customerId, @Context CustomerRepository customerRepository) {
        if (customerId == null) return null;
        return customerRepository.findById(customerId).orElse(null);
    }

    default Long map(Customer customer) {
        return customer != null ? customer.getId() : null;
    }
    
    default Operator map(Long operatorId, @Context OperatorRepository operatorRepository) {
        if (operatorId == null) return null;
        return operatorRepository.findById(operatorId).orElse(null);
    }

    default String mapCustomerNameAndPhone(Customer customer) {
        if (customer == null) return null;
        return customer.getName() + " " + customer.getSurname() + " - " + customer.getPhoneNumber();
    }
    
    default String mapOperatorNameAndPhone(Operator operator) {
        if (operator == null) return null;
        return operator.getName() + " " + operator.getSurname() + " - " + operator.getPhoneNumber();
    }
}
