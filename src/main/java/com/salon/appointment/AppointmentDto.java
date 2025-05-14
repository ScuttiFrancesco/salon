package com.salon.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.salon.enums.Service;
import lombok.Data;

@Data
public class AppointmentDto {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private Long customerId;
    private List<Service> service;
}
