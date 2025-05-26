package com.salon.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.salon.enums.CustomerSearchDirection;
import com.salon.enums.CustomerSearchType;

import jakarta.validation.Valid;

@RestController
@RequestMapping({ "/api/customer", "/api/0" })
public class CustomerController {

    private final ICustomerService customerService;
    private PaginationInfo paginationInfo;
    private PaginatedResponse<List<CustomerDto>> paginatedResponse;
    private int totalElements;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
        this.paginationInfo = new PaginationInfo();
        this.paginatedResponse = new PaginatedResponse<>();
        this.totalElements = (int) customerService.countAll();
    }

    @PostMapping
    public ResponseEntity<CustomerDto> insert(@Valid @RequestBody CustomerDto customer) {
        CustomerDto createdCustomer = customerService.insert(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@Valid @RequestBody CustomerDto customer) {
        CustomerDto updatedCustomer = customerService.update(customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable Long id) {
        CustomerDto customer = customerService.findById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/retrieveAll")
    public ResponseEntity<List<CustomerDto>> findAll() {
        List<CustomerDto> customers = customerService.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/searchByName={input}")
    public ResponseEntity<PaginatedResponse<List<CustomerDto>>> findByName(@PathVariable String input,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int sortBy,
            @RequestParam int sortDir) {
        List<CustomerDto> customers = customerService.findBySearch(CustomerSearchType.NAME, input, page - 1,
                size,
                CustomerSearchType.values()[sortBy], CustomerSearchDirection.values()[sortDir]);
        this.paginationInfo = paginationInfo(page, size, sortBy, sortDir);

        this.paginatedResponse.setData(customers);
        this.paginatedResponse.setPagination(paginationInfo);

        return ResponseEntity.ok(this.paginatedResponse);
    }

    @GetMapping("/searchByEmail={input}")
    public ResponseEntity<PaginatedResponse<List<CustomerDto>>> findByEmail(@PathVariable String input,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int sortBy,
            @RequestParam int sortDir) {
        List<CustomerDto> customers = customerService.findBySearch(CustomerSearchType.EMAIL, input, page - 1,
                size,
                CustomerSearchType.values()[sortBy], CustomerSearchDirection.values()[sortDir]);
        this.paginationInfo = paginationInfo(page, size, sortBy, sortDir);

        this.paginatedResponse.setData(customers);
        this.paginatedResponse.setPagination(paginationInfo);

        return ResponseEntity.ok(this.paginatedResponse);
    }

    @GetMapping("/searchByPhoneNumber={input}")
    public ResponseEntity<PaginatedResponse<List<CustomerDto>>> findByPhoneNumber(@PathVariable String input,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int sortBy,
            @RequestParam int sortDir) {
        List<CustomerDto> customers = customerService.findBySearch(CustomerSearchType.PHONE_NUMBER, input, page - 1,
                size,
                CustomerSearchType.values()[sortBy], CustomerSearchDirection.values()[sortDir]);
        this.paginationInfo = paginationInfo(page, size, sortBy, sortDir);

        this.paginatedResponse.setData(customers);
        this.paginatedResponse.setPagination(paginationInfo);

        return ResponseEntity.ok(this.paginatedResponse);
    }

    @GetMapping("/retrieveAll/paginated")
    public ResponseEntity<PaginatedResponse<List<CustomerDto>>> findByPagination(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam int sortBy,
            @RequestParam int sortDir) {

        this.paginationInfo = paginationInfo(page, size, sortBy, sortDir);

        List<CustomerDto> customers = customerService.findByPagination(page - 1, size,
                CustomerSearchType.values()[sortBy], CustomerSearchDirection.values()[sortDir]);

        this.paginatedResponse.setData(customers);
        this.paginatedResponse.setPagination(paginationInfo);

        return ResponseEntity.ok(this.paginatedResponse);
    }

    private PaginationInfo paginationInfo(int page, int size, int sortBy, int sortDir) {

        CustomerSearchType sortByEnum = CustomerSearchType.values()[sortBy];
        CustomerSearchDirection sortDirEnum = CustomerSearchDirection.values()[sortDir];

        int pagina = page - 1;
        if (sortBy < 0 || sortBy >= CustomerSearchType.values().length ||
                sortDir < 0 || sortDir >= CustomerSearchDirection.values().length) {
            throw new IllegalArgumentException("Parametri di paginazione non validi");
        }

        int totalPages = (int) Math.ceil((double) this.totalElements / size);

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
