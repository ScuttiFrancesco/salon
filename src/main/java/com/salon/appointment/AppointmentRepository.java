package com.salon.appointment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCustomerId(Long customerId);

    List<Appointment> findByDate(LocalDateTime date);

    boolean existsByDateAndCustomerId(LocalDateTime date,Long customerId);

    boolean existsByDateAndCustomerIdAndIdNot(LocalDateTime date,Long customerId, Long id);

    List<Appointment> findByDateAndCustomerId(LocalDateTime date, Long customerId);

    List<Appointment> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

     List<Appointment> findByCustomerNameStartingWithIgnoreCaseOrCustomerSurnameStartingWithIgnoreCase(String namePrefix,
            String surnamePrefix);

    // Paginated search methods
    List<Appointment> findByCustomerNameStartingWithIgnoreCaseOrCustomerSurnameStartingWithIgnoreCase(Pageable pageable, String namePrefix,
            String surnamePrefix);

    List<Appointment> findByOperatorNameStartingWithIgnoreCaseOrOperatorSurnameStartingWithIgnoreCase(Pageable pageable, String namePrefix,
            String surnamePrefix);

    List<Appointment> findByNotesContainingIgnoreCase(Pageable pageable, String notes);

    // Non-paginated versions for counting
    List<Appointment> findByOperatorNameStartingWithIgnoreCaseOrOperatorSurnameStartingWithIgnoreCase(String namePrefix,
            String surnamePrefix);

    List<Appointment> findByNotesContainingIgnoreCase(String notes);

    // Paginated version for date range and customer name search
    List<Appointment> findByDateBetweenAndCustomerNameStartingWithIgnoreCaseOrCustomerSurnameStartingWithIgnoreCase(
            Pageable pageable, LocalDateTime startDate, LocalDateTime endDate, String namePrefix, String surnamePrefix);

    // Non-paginated version for counting
    List<Appointment> findByDateBetweenAndCustomerNameStartingWithIgnoreCaseOrCustomerSurnameStartingWithIgnoreCase(
            LocalDateTime startDate, LocalDateTime endDate, String namePrefix, String surnamePrefix);
}
