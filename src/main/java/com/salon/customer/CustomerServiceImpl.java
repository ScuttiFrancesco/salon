package com.salon.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salon.enums.CustomerSearchDirection;
import com.salon.enums.CustomerSearchType;
import com.salon.exception.DuplicateDataException;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDto insert(CustomerDto customer) {

        String duplicateField = null;
        if (customerRepository.existsByNameAndSurname(customer.getName(), customer.getSurname())) {
            duplicateField = "Cliente";
        } else if (customerRepository.existsByEmail(customer.getEmail())) {
            duplicateField = "Email";
        } else if (customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
            duplicateField = "Telefono";
        }
        if (duplicateField != null) {
            throw new DuplicateDataException(duplicateField + " già presente in archivio");
        }
        return customerMapper.toDto(customerRepository.save(customerMapper.toEntity(customer)));
    }

    @Override
    public CustomerDto update(CustomerDto customer) {

        @SuppressWarnings("unused")
        Customer existing = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        String duplicateField = null;
        if (customerRepository.existsByNameAndSurnameAndIdNot(customer.getName(), customer.getSurname(),
                customer.getId())) {
            duplicateField = "Cliente";
        } else if (customerRepository.existsByEmailAndIdNot(customer.getEmail(), customer.getId())) {
            duplicateField = "Email";
        } else if (customerRepository.existsByPhoneNumberAndIdNot(customer.getPhoneNumber(), customer.getId())) {
            duplicateField = "Telefono";
        }
        if (duplicateField != null) {
            throw new DuplicateDataException(duplicateField + " già presente in archivio");
        }
        return customerMapper.toDto(customerRepository.save(customerMapper.toEntity(customer)));
    }

    @Override
    public void deleteById(Long id) {

        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        customerRepository.deleteById(existing.getId());

        if (customerRepository.existsById(id)) {
            throw new RuntimeException("Eliminazione non riuscita");
        }

    }

    @Override
    public CustomerDto findById(Long id) {

        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente non presente in archivio"));

        return customerMapper.toDto(existing);
    }

    @Override
    public List<CustomerDto> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    @Override
    public List<CustomerDto> findBySearch(CustomerSearchType type, String input, int page, int size,
            CustomerSearchType sortBy, CustomerSearchDirection sortDir) {

        Pageable pageable = pagination(sortBy, sortDir, page, size);
        switch (type) {
            case NAME:
                return customerRepository
                        .findByNameStartingWithIgnoreCaseOrSurnameStartingWithIgnoreCase(pageable ,input, input)
                        .stream()
                        .map(customerMapper::toDto)
                        .toList();

            case EMAIL:
                return customerRepository
                        .findByEmailStartingWithIgnoreCase(pageable,input)
                        .stream()
                        .map(customerMapper::toDto)
                        .toList();

            case PHONE_NUMBER:
                return customerRepository
                        .findByPhoneNumberStartingWithIgnoreCase(pageable,input)
                        .stream()
                        .map(customerMapper::toDto)
                        .toList();

            default:
                return List.of();
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> findByPagination(int page, int size, CustomerSearchType sortBy,
            CustomerSearchDirection sortDir) {
        Pageable pageable = pagination(sortBy, sortDir, page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        return customerPage.getContent()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long countAll() {
        return customerRepository.count();
    }

    private Pageable pagination(CustomerSearchType sortBy, CustomerSearchDirection sortDir, int page, int size) {
        String sortField = switch (sortBy) {
            case ID -> "id";
            case NAME -> "name";
            case SURNAME -> "surname";
            case EMAIL -> "email";
            case PHONE_NUMBER -> "phoneNumber";
            default -> "id";
        };

        Sort.Direction direction = sortDir == CustomerSearchDirection.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return pageable;
    }
}
