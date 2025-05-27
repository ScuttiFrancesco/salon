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

import com.salon.dto.PaginatedResponse;
import com.salon.dto.PaginationInfo;
import com.salon.enums.AppointmentSearchDirection;
import com.salon.enums.AppointmentSearchType;

import jakarta.validation.Valid;

@RestController
@RequestMapping({ "/api/appointment", "/api/1" })
public class AppointmentController {

    private final IAppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;
    private PaginationInfo paginationInfo;
    private PaginatedResponse<List<AppointmentDto>> paginatedResponse;
    private int totalElements;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService, AppointmentRepository appointmentRepository) {
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
        this.paginationInfo = new PaginationInfo();
        this.paginatedResponse = new PaginatedResponse<>();
        this.totalElements = (int) appointmentService.countAll();
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

    @GetMapping("/searchByCustomerName={input}")
    public ResponseEntity<PaginatedResponse<List<AppointmentDto>>> findByCustomerName(@PathVariable String input,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int sortBy,
            @RequestParam int sortDir) {
        int totalElements = appointmentRepository.findByCustomerNameStartingWithIgnoreCaseOrCustomerSurnameStartingWithIgnoreCase(input, input).size();
        List<AppointmentDto> appointments = appointmentService.findBySearch(AppointmentSearchType.CUSTOMER_NAME, input, page - 1,
                size,
                AppointmentSearchType.values()[sortBy], AppointmentSearchDirection.values()[sortDir]);
        this.paginationInfo = paginationInfo(page, size, totalElements, sortBy, sortDir);

        this.paginatedResponse.setData(appointments);
        this.paginatedResponse.setPagination(paginationInfo);

        return ResponseEntity.ok(this.paginatedResponse);
    }

    @GetMapping("/searchByOperatorName={input}")
    public ResponseEntity<PaginatedResponse<List<AppointmentDto>>> findByOperatorName(@PathVariable String input,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int sortBy,
            @RequestParam int sortDir) {
        int totalElements = appointmentRepository.findByOperatorNameStartingWithIgnoreCaseOrOperatorSurnameStartingWithIgnoreCase(input, input).size();
        List<AppointmentDto> appointments = appointmentService.findBySearch(AppointmentSearchType.OPERATOR_NAME, input, page - 1,
                size,
                AppointmentSearchType.values()[sortBy], AppointmentSearchDirection.values()[sortDir]);
        this.paginationInfo = paginationInfo(page, size, totalElements, sortBy, sortDir);

        this.paginatedResponse.setData(appointments);
        this.paginatedResponse.setPagination(paginationInfo);

        return ResponseEntity.ok(this.paginatedResponse);
    }

    @GetMapping("/searchByDuration={input}")
    public ResponseEntity<PaginatedResponse<List<AppointmentDto>>> findByNotes(@PathVariable String input,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int sortBy,
            @RequestParam int sortDir) {
        int totalElements = appointmentRepository.findByNotesContainingIgnoreCase(input).size();
        List<AppointmentDto> appointments = appointmentService.findBySearch(AppointmentSearchType.DURATION, input, page - 1,
                size,
                AppointmentSearchType.values()[sortBy], AppointmentSearchDirection.values()[sortDir]);
        this.paginationInfo = paginationInfo(page, size, totalElements, sortBy, sortDir);

        this.paginatedResponse.setData(appointments);
        this.paginatedResponse.setPagination(paginationInfo);

        return ResponseEntity.ok(this.paginatedResponse);
    }

    @GetMapping("/retrieveAll/paginated")
    public ResponseEntity<PaginatedResponse<List<AppointmentDto>>> findByPagination(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int sortBy,
            @RequestParam int sortDir) {

        List<AppointmentDto> appointments = appointmentService.findByPagination(page - 1, size,
                AppointmentSearchType.values()[sortBy], AppointmentSearchDirection.values()[sortDir]);
        this.paginationInfo = paginationInfo(page, size, this.totalElements, sortBy, sortDir);

        this.paginatedResponse.setData(appointments);
        this.paginatedResponse.setPagination(paginationInfo);

        return ResponseEntity.ok(this.paginatedResponse);
    }

    @GetMapping("/dateRange={startDate}/{endDate}")
    public ResponseEntity<PaginatedResponse<List<AppointmentDto>>> findByDateBetween(
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int sortBy,
            @RequestParam int sortDir) {
        
        List<AppointmentDto> allAppointments = appointmentService.findByDateBetween(startDate, endDate);
        int totalElements = allAppointments.size();
        
        // Apply pagination manually since we need date filtering first
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        List<AppointmentDto> paginatedAppointments = allAppointments.subList(startIndex, endIndex);
        
        this.paginationInfo = paginationInfo(page, size, totalElements, sortBy, sortDir);
        this.paginatedResponse.setData(paginatedAppointments);
        this.paginatedResponse.setPagination(paginationInfo);
        
        return ResponseEntity.ok(this.paginatedResponse);
    }

    @GetMapping("/dateRange={startDate}/{endDate}/customerName={input}")
    public ResponseEntity<PaginatedResponse<List<AppointmentDto>>> findByDateBetweenAndCustomer(
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, 
            @PathVariable String input,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int sortBy,
            @RequestParam int sortDir) {
        
        List<AppointmentDto> allAppointments = appointmentService.findByDateBetweenAndCustomerName(
            startDate, endDate, input, page - 1, size, 
            AppointmentSearchType.values()[sortBy], AppointmentSearchDirection.values()[sortDir]);
        int totalElements = allAppointments.size();
        
        // Apply pagination manually since we need date and customer filtering first
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        List<AppointmentDto> paginatedAppointments = allAppointments.subList(startIndex, endIndex);
        
        this.paginationInfo = paginationInfo(page, size, totalElements, sortBy, sortDir);
        this.paginatedResponse.setData(paginatedAppointments);
        this.paginatedResponse.setPagination(paginationInfo);
        
        return ResponseEntity.ok(this.paginatedResponse);
    }

    private PaginationInfo paginationInfo(int page, int size, int totalElements, int sortBy, int sortDir) {

        AppointmentSearchType sortByEnum = AppointmentSearchType.values()[sortBy];
        AppointmentSearchDirection sortDirEnum = AppointmentSearchDirection.values()[sortDir];

        int pagina = page - 1;
        if (sortBy < 0 || sortBy >= AppointmentSearchType.values().length ||
                sortDir < 0 || sortDir >= AppointmentSearchDirection.values().length) {
            throw new IllegalArgumentException("Parametri di paginazione non validi");
        }

        int totalPages = (int) Math.ceil((double) totalElements / size);

        paginationInfo.setCurrentPage(page);
        paginationInfo.setPageSize(size);
        paginationInfo.setTotalElements((int) totalElements);
        paginationInfo.setTotalPages(totalPages);
        paginationInfo.setHasNext(pagina < totalPages - 1);
        paginationInfo.setHasPrevious(pagina > 1);
        paginationInfo.setSortBy(sortByEnum.getValue());
        paginationInfo.setSortDirection(sortDirEnum.getValue());
        return paginationInfo;
    }
}
