package com.salon.customer;

import java.util.List;

import org.springframework.data.domain.PageRequest;
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

    List<Customer> findByEmailStartingWithIgnoreCase(String emailPrefix);

    List<Customer> findByPhoneNumberStartingWithIgnoreCase(String phoneNumberPrefix);

    List<Customer> findAllByOrderById(PageRequest pageRequest);

    List<Customer> findAllByOrderByIdDesc(PageRequest pageRequest);

    List<Customer> findAllByOrderByName(PageRequest pageRequest);

    List<Customer> findAllByOrderByNameDesc(PageRequest pageRequest);

    List<Customer> findAllByOrderBySurname(PageRequest pageRequest);

    List<Customer> findAllByOrderBySurnameDesc(PageRequest pageRequest);

    List<Customer> findAllByOrderByEmail(PageRequest pageRequest);

    List<Customer> findAllByOrderByEmailDesc(PageRequest pageRequest);

    List<Customer> findAllByOrderByPhoneNumber(PageRequest pageRequest);

    List<Customer> findAllByOrderByPhoneNumberDesc(PageRequest pageRequest);

}
