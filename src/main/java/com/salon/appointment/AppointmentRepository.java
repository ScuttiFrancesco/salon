package com.salon.appointment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salon.customer.Customer;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCustomerId(Long customerId);

    List<Appointment> findByDate(LocalDateTime date);

    boolean existsByDateAndCustomerId(LocalDateTime date,Long customerId);

    boolean existsByDateAndCustomerIdAndIdNot(LocalDateTime date,Long customerId, Long id);

    List<Appointment> findByDateAndCustomerId(LocalDateTime date, Long customerId);

    List<Appointment> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Appointment> findByDateBetweenAndCustomerId(LocalDateTime startDate, LocalDateTime endDate, Long customerId);

     List<Appointment> findByCustomerNameStartingWithIgnoreCaseOrCustomerSurnameStartingWithIgnoreCase(String namePrefix,
            String surnamePrefix);

}
