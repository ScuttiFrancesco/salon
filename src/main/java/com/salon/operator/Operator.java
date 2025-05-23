package com.salon.operator;

import java.time.LocalDate;
import java.util.List;
import com.salon.appointment.Appointment;
import com.salon.person.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "operator")
public class Operator extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
    private LocalDate hiringDate;
    private String position;
    private int level;
    @OneToMany(mappedBy = "operator")
    private List<Appointment> appointments;


    
}
