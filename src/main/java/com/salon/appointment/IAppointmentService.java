package com.salon.appointment;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentService {
    AppointmentDto insert(AppointmentDto appointmentDto);
    AppointmentDto update(AppointmentDto appointmentDto);
    void deleteById(Long id);
    AppointmentDto findById(Long id);
    List<AppointmentDto> findAll();
    
    List<AppointmentDto> findByCustomerId(Long customerId);
    List<AppointmentDto> findByDate(LocalDate date);
    List<AppointmentDto> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<AppointmentDto> findByDateBetweenAndCustomerId(LocalDate startDate, LocalDate endDate, Long customerId);
    List<AppointmentDto> findByDateAndCustomerId(LocalDate date, Long customerId);
    List<AppointmentDto> findByCustomerNameOrCustomerSurname(String input);
}
