package com.salon.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salon.customer.Customer;
import com.salon.customer.CustomerRepository;
import com.salon.operator.Operator;
import com.salon.operator.OperatorRepository;
import com.salon.exception.DuplicateDataException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final CustomerRepository customerRepository;
    private final OperatorRepository operatorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper,
            CustomerRepository customerRepository, OperatorRepository operatorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.customerRepository = customerRepository;
        this.operatorRepository = operatorRepository;
    }

    @Override
    @Transactional
    public AppointmentDto insert(AppointmentDto appointment) {

        if (appointmentRepository.existsByDateAndCustomerId(appointment.getDate(), appointment.getCustomerId())) {
            throw new DuplicateDataException("Un appuntamento esiste già con la stessa data e ID cliente");
        }
         if (appointment.getDate().toLocalDate().isBefore(LocalDate.now())) {
            throw new DuplicateDataException("La data dell'appuntamento non può essere nel passato");
        }
        if (appointment.getDate().isAfter(LocalDateTime.now().plusDays(90))) {
            throw new DuplicateDataException("La data dell'appuntamento non può essere oltre 90 giorni nel futuro");
        }
        if (appointment.getDate().getHour() < 8 || appointment.getDate().getHour() > 20) {
            throw new DuplicateDataException("L'orario dell'appuntamento deve essere compreso tra le 8:00 e le 20:00");
        }
        Customer existingCustomer = customerRepository.findById(appointment.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente non presente in archivio"));

        Operator operator = null;
        if (appointment.getOperatorId() != null) {
            operator = operatorRepository.findById(appointment.getOperatorId())
                    .orElseThrow(() -> new EntityNotFoundException("Operatore non presente in archivio"));
        }

        Appointment newAppointment = new Appointment();
        newAppointment.setDate(appointment.getDate());
        newAppointment.setDuration(appointment.getDuration());
        newAppointment.setNotes(appointment.getNotes());
        newAppointment.setCustomer(existingCustomer);
        newAppointment.setServices(appointment.getServices());
        if (operator != null) {
            newAppointment.setOperator(operator);
        }

        Appointment savedAppointment = appointmentRepository.save(newAppointment);
        entityManager.flush();
        return appointmentMapper.toDto(savedAppointment);
    }

    @Override
    @Transactional
    public AppointmentDto update(AppointmentDto appointment) {

        Appointment existing = appointmentRepository.findById(appointment.getId())
                .orElseThrow(() -> new EntityNotFoundException("Appuntamento non presente in archivio"));

        if (appointmentRepository.existsByDateAndCustomerIdAndIdNot(appointment.getDate(), appointment.getCustomerId(),
                appointment.getId())) {
            throw new DuplicateDataException("Un appuntamento esiste già con la stessa data e ID cliente");
        }

        if (appointment.getDate().toLocalDate().isBefore(LocalDate.now())) {
            throw new DuplicateDataException("La data dell'appuntamento non può essere nel passato");
        }
        if (appointment.getDate().isAfter(LocalDateTime.now().plusDays(90))) {
            throw new DuplicateDataException("La data dell'appuntamento non può essere oltre 90 giorni nel futuro");
        }
        if (appointment.getDate().getHour() < 8 || appointment.getDate().getHour() > 20) {
            throw new DuplicateDataException("L'orario dell'appuntamento deve essere compreso tra le 8:00 e le 20:00");
        }

        Customer existingCustomer = customerRepository.findById(appointment.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente non presente in archivio"));

        Operator operator = null;
        if (appointment.getOperatorId() != null) {
            operator = operatorRepository.findById(appointment.getOperatorId())
                    .orElseThrow(() -> new EntityNotFoundException("Operatore non presente in archivio"));
        }

        existing.setDate(appointment.getDate());
        existing.setDuration(appointment.getDuration());
        existing.setNotes(appointment.getNotes());
        existing.setCustomer(existingCustomer);
        existing.setServices(appointment.getServices());
        if (operator != null) {
            existing.setOperator(operator);
        }

        Appointment updated = appointmentRepository.save(existing);
        entityManager.flush();

        return appointmentMapper.toDto(updated);
    }

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
    public AppointmentDto findById(Long id) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appuntamento non presente in archivio"));

        return appointmentMapper.toDto(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> findAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> findByCustomerId(Long customerId) {
        return appointmentRepository.findByCustomerId(customerId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> findByDate(LocalDate date) {
        LocalDateTime DateTime = date.atStartOfDay();
        return appointmentRepository.findByDate(DateTime)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> findByDateAndCustomerId(LocalDate date, Long customerId) {
        LocalDateTime DateTime = date.atStartOfDay();

        return appointmentRepository.findByDateAndCustomerId(DateTime, customerId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return appointmentRepository.findByDateBetween(startDateTime, endDateTime)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> findByDateBetweenAndCustomerId(LocalDate startDate, LocalDate endDate,
            Long customerId) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return appointmentRepository.findByDateBetweenAndCustomerId(startDateTime, endDateTime, customerId)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDto> findByCustomerNameOrCustomerSurname(String input) {
        return appointmentRepository
                .findByCustomerNameStartingWithIgnoreCaseOrCustomerSurnameStartingWithIgnoreCase(input, input)
                .stream()
                .map(appointmentMapper::toDto)
                .toList();
    }
}
