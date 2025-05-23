package com.salon.appointment;

import java.time.LocalDateTime;
import java.util.List;

import com.salon.enums.Service;
import lombok.Data;

@Data
public class AppointmentDto {
    private Long id;
    private LocalDateTime date;
    private double duration;
    private String notes;
    private Long customerId;
    private Long operatorId;
    private String customerNameAndPhone;
    private String operatorNameAndPhone; 
    private List<Service> services;
}
