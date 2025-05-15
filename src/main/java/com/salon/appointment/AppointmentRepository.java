package com.salon.appointment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCustomerId(Long customerId);

    List<Appointment> findByDate(LocalDate date);

    boolean existsByDateAndCustomerId(LocalDate date,Long customerId);

    boolean existsByDateAndCustomerIdAndIdNot(LocalDate date,Long customerId, Long id);

    List<Appointment> findByDateAndCustomerId(LocalDate date, Long customerId);

    List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Appointment> findByDateBetweenAndCustomerId(LocalDate startDate, LocalDate endDate, Long customerId);

}
