package com.salon.appointment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final IAppointmentService appointmentService;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> insert(@Valid @RequestBody AppointmentDto appointment) {
        AppointmentDto createdAppointment = appointmentService.insert(appointment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> update(@Valid @RequestBody AppointmentDto appointment) {
        AppointmentDto updatedAppointment = appointmentService.update(appointment);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> findById(@PathVariable Long id) {
        AppointmentDto appointment = appointmentService.findById(id);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/retrieveAll")
    public ResponseEntity<List<AppointmentDto>> findAll() {
        List<AppointmentDto> appointments = appointmentService.findAll();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date-range/{startDate}/{endDate}")
    public ResponseEntity<List<AppointmentDto>> findByDateBetween(@PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<AppointmentDto> appointments = appointmentService.findByDateBetween(startDate, endDate);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/date-range/{startDate}/{endDate}/customer/{customerId}")
    public ResponseEntity<List<AppointmentDto>> findByDateBetween(@PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
    @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, @PathVariable Long customerId) {
        List<AppointmentDto> appointments = appointmentService.findByDateBetweenAndCustomerId(startDate, endDate, customerId);
        return ResponseEntity.ok(appointments);
    }

}
