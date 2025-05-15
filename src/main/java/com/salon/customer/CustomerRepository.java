package com.salon.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByNameAndSurname(String name, String surname);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNameAndSurnameAndIdNot(String name, String surname, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    List<Customer> findByNameStartingWithIgnoreCaseOrSurnameStartingWithIgnoreCase(String namePrefix,
            String surnamePrefix);
}
