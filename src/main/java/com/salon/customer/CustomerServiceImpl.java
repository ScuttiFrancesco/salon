package com.salon.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salon.enums.CustomerSearchType;
import com.salon.exception.DuplicateDataException;
import jakarta.persistence.EntityNotFoundException;
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
            duplicateField = "Name and surname";
        } else if (customerRepository.existsByEmail(customer.getEmail())) {
            duplicateField = "Email";
        } else if (customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
            duplicateField = "Phone number";
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
            duplicateField = "Name and surname";
        } else if (customerRepository.existsByEmailAndIdNot(customer.getEmail(), customer.getId())) {
            duplicateField = "Email";
        } else if (customerRepository.existsByPhoneNumberAndIdNot(customer.getPhoneNumber(), customer.getId())) {
            duplicateField = "Phone number";
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
    public List<CustomerDto> findBySearch(CustomerSearchType type, String input) {

        switch (type) {
            case NAME:
                return customerRepository
                        .findByNameStartingWithIgnoreCaseOrSurnameStartingWithIgnoreCase(input, input)
                        .stream()
                        .map(customerMapper::toDto)
                        .toList();

            case EMAIL:
                return customerRepository
                        .findByEmailStartingWithIgnoreCase(input)
                        .stream()
                        .map(customerMapper::toDto)
                        .toList();

            case PHONE_NUMBER:
                return customerRepository
                        .findByPhoneNumberStartingWithIgnoreCase(input)
                        .stream()
                        .map(customerMapper::toDto)
                        .toList();

            default:
                return List.of();
        }

    }
}
