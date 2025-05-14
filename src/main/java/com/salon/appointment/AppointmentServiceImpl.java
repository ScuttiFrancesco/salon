package com.salon.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salon.exception.DuplicateDataException;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;


@Service
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public AppointmentDto insert(AppointmentDto appointment) {
        if (appointmentRepository.existsByDateAndCustomerId(appointment.getDate(), appointment.getCustomerId())) {
            throw new DuplicateDataException("An appointment already exists with the same date and customer ID");
        }

        return appointmentMapper.toDto(appointmentRepository.save(appointmentMapper.toEntity(appointment)));
    }

    @Override
    public AppointmentDto update(AppointmentDto appointment) {

        @SuppressWarnings("unused")
        Appointment existing = appointmentRepository.findById(appointment.getId())
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        if (appointmentRepository.existsByDateAndCustomerIdAndIdNot(appointment.getDate(), appointment.getCustomerId(),
                appointment.getId())) {
            throw new DuplicateDataException("An appointment already exists with the same date and customer ID");
        }

        return appointmentMapper.toDto(appointmentRepository.save(appointmentMapper.toEntity(appointment)));
    }

    @Override
    public void deleteById(Long id) {
        @SuppressWarnings("unused")
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        if (appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment deletion failed");
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public AppointmentDto findById(Long id) {
         Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        return appointmentMapper.toDto(existing);
    }

    @Override
    public List<AppointmentDto> findAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> findByCustomerId(Long customerId) {
        return appointmentRepository.findByCustomerId(customerId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> findByDate(LocalDate date) {
        return appointmentRepository.findByDate(date)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> findByDateAndCustomerId(LocalDate date, Long customerId) {
       return appointmentRepository.findByDateAndCustomerId(date, customerId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
        
    }

    @Override
    public List<AppointmentDto> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findByDateBetween(startDate, endDate)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    public List<AppointmentDto> findByDateBetweenAndCustomerId(LocalDate startDate, LocalDate endDate,
            Long customerId) {
       return appointmentRepository.findByDateBetweenAndCustomerId(startDate, endDate, customerId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }
}
