package com.salon.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salon.customer.CustomerRepository;
import com.salon.exception.DuplicateDataException;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper,
            CustomerRepository customerRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public AppointmentDto insert(AppointmentDto appointment) {
        if (appointmentRepository.existsByDateAndCustomerId(appointment.getDate(), appointment.getCustomerId())) {
            throw new DuplicateDataException("Un appuntamento esiste già con la stessa data e ID cliente");
        }

        return appointmentMapper
                .toDto(appointmentRepository.save(appointmentMapper.toEntity(appointment, customerRepository)));
    }

    @Override
    public AppointmentDto update(AppointmentDto appointment) {

        @SuppressWarnings("unused")
        Appointment existing = appointmentRepository.findById(appointment.getId())
                .orElseThrow(() -> new EntityNotFoundException("Appuntamento non presente in archivio"));

        if (appointmentRepository.existsByDateAndCustomerIdAndIdNot(appointment.getDate(), appointment.getCustomerId(),
                appointment.getId())) {
            throw new DuplicateDataException("Un appuntamento esiste già con la stessa data e ID cliente");
        }

        return appointmentMapper
                .toDto(appointmentRepository.save(appointmentMapper.toEntity(appointment, customerRepository)));
    }

    @Override
    public void deleteById(Long id) {
        @SuppressWarnings("unused")
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appuntamento non presente in archivio"));

        appointmentRepository.deleteById(id);

        if (appointmentRepository.existsById(id)) {
            throw new RuntimeException("Eliminazione non riuscita");
        }

    }

    @Override
    public AppointmentDto findById(Long id) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appuntamento non presente in archivio"));

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
