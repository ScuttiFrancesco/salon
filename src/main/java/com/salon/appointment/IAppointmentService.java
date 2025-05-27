package com.salon.appointment;

import java.time.LocalDate;
import java.util.List;

import com.salon.enums.AppointmentSearchDirection;
import com.salon.enums.AppointmentSearchType;

public interface IAppointmentService {
    AppointmentDto insert(AppointmentDto appointmentDto);
    AppointmentDto update(AppointmentDto appointmentDto);
    void deleteById(Long id);
    AppointmentDto findById(Long id);
    List<AppointmentDto> findAll();
    
    List<AppointmentDto> findByCustomerId(Long customerId);
    List<AppointmentDto> findByDate(LocalDate date);
    List<AppointmentDto> findByDateBetween(LocalDate startDate, LocalDate endDate);
 
    List<AppointmentDto> findByCustomerNameOrCustomerSurname(String input);

    /**
     * Trova appuntamenti con paginazione.
     *
     * @param page numero della pagina
     * @param size dimensione della pagina
     * @param sortBy tipo di campo per ordinamento
     * @param sortDir direzione dell'ordinamento
     * @return lista paginata degli appuntamenti
     */
    List<AppointmentDto> findByPagination(int page, int size, AppointmentSearchType sortBy, AppointmentSearchDirection sortDir);

    /**
     * Conta tutti gli appuntamenti.
     *
     * @return numero totale di appuntamenti
     */
    long countAll();

    /**
     * Cerca appuntamenti con paginazione.
     *
     * @param type tipo di ricerca
     * @param input stringa di ricerca
     * @param page numero della pagina
     * @param size dimensione della pagina
     * @param sortBy tipo di campo per ordinamento
     * @param sortDir direzione dell'ordinamento
     * @return lista paginata degli appuntamenti
     */
    List<AppointmentDto> findBySearch(AppointmentSearchType type, String input, int page, int size, AppointmentSearchType sortBy, AppointmentSearchDirection sortDir);

    /**
     * Trova appuntamenti per intervallo di date e nome cliente con paginazione.
     *
     * @param startDate data di inizio
     * @param endDate data di fine
     * @param input nome o cognome del cliente
     * @param page numero della pagina
     * @param size dimensione della pagina
     * @param sortBy tipo di campo per ordinamento
     * @param sortDir direzione dell'ordinamento
     * @return lista paginata degli appuntamenti
     */
    List<AppointmentDto> findByDateBetweenAndCustomerName(LocalDate startDate, LocalDate endDate,
            String input, int page, int size, AppointmentSearchType sortBy, AppointmentSearchDirection sortDir);
}
