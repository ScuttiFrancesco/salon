package com.salon.operator;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

    boolean existsByNameAndSurname(String name, String surname);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNameAndSurnameAndIdNot(String name, String surname, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    List<Operator> findByNameStartingWithIgnoreCaseOrSurnameStartingWithIgnoreCase(String namePrefix,
            String surnamePrefix);
    List<Operator> findByEmailStartingWithIgnoreCase(String emailPrefix);
    List<Operator> findByPhoneNumberStartingWithIgnoreCase(String phoneNumberPrefix);
    List<Operator> findByPositionStartingWithIgnoreCase(String positionPrefix);
    List<Operator> findByLevel(int level);
}
