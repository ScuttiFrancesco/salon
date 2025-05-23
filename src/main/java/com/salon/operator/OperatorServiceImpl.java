package com.salon.operator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salon.exception.DuplicateDataException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OperatorServiceImpl implements IOperatorService {

    private final OperatorRepository operatorRepository;
    private final OperatorMapper operatorMapper;

    @Autowired
    public OperatorServiceImpl(OperatorRepository operatorRepository, OperatorMapper operatorMapper) {
        this.operatorRepository = operatorRepository;
        this.operatorMapper = operatorMapper;
    }

    @Override
    @Transactional
    public OperatorDto insert(OperatorDto operator) {
        String duplicateField = null;
        if (operatorRepository.existsByNameAndSurname(operator.getName(), operator.getSurname())) {
            duplicateField = "Operatore";
        } else if (operatorRepository.existsByEmail(operator.getEmail())) {
            duplicateField = "Email";
        } else if (operatorRepository.existsByPhoneNumber(operator.getPhoneNumber())) {
            duplicateField = "Telefono";
        }
        if (duplicateField != null) {
            throw new DuplicateDataException(duplicateField + " già presente in archivio");
        }
        return operatorMapper.toDto(operatorRepository.save(operatorMapper.toEntity(operator)));
    }

    @Override
    @Transactional
    public OperatorDto update(OperatorDto operator) {
        @SuppressWarnings("unused")
        Operator existing = operatorRepository.findById(operator.getId())
                .orElseThrow(() -> new EntityNotFoundException("Operatore non trovato"));

        String duplicateField = null;
        if (operatorRepository.existsByNameAndSurnameAndIdNot(operator.getName(), operator.getSurname(),
                operator.getId())) {
            duplicateField = "Operatore";
        } else if (operatorRepository.existsByEmailAndIdNot(operator.getEmail(), operator.getId())) {
            duplicateField = "Email";
        } else if (operatorRepository.existsByPhoneNumberAndIdNot(operator.getPhoneNumber(), operator.getId())) {
            duplicateField = "Telefono";
        }
        if (duplicateField != null) {
            throw new DuplicateDataException(duplicateField + " già presente in archivio");
        }
        return operatorMapper.toDto(operatorRepository.save(operatorMapper.toEntity(operator)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Operator existing = operatorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operatore non trovato"));

        operatorRepository.deleteById(existing.getId());

        if (operatorRepository.existsById(id)) {
            throw new RuntimeException("Eliminazione non riuscita");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OperatorDto findById(Long id) {
        Operator existing = operatorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operatore non presente in archivio"));

        return operatorMapper.toDto(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperatorDto> findAll() {
        return operatorRepository.findAll()
                .stream()
                .map(operatorMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperatorDto> findByNameOrSurname(String input) {
        return operatorRepository
                .findByNameStartingWithIgnoreCaseOrSurnameStartingWithIgnoreCase(input, input)
                .stream()
                .map(operatorMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperatorDto> findByEmail(String input) {
        return operatorRepository
                .findByEmailStartingWithIgnoreCase(input)
                .stream()
                .map(operatorMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperatorDto> findByPhoneNumber(String input) {
        return operatorRepository
                .findByPhoneNumberStartingWithIgnoreCase(input)
                .stream()
                .map(operatorMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperatorDto> findByPosition(String position) {
        return operatorRepository
                .findByPositionStartingWithIgnoreCase(position)
                .stream()
                .map(operatorMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OperatorDto> findByLevel(int level) {
        return operatorRepository
                .findByLevel(level)
                .stream()
                .map(operatorMapper::toDto)
                .toList();
    }
}
