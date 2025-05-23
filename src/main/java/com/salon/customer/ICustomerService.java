package com.salon.customer;

import java.util.List;

import com.salon.enums.CustomerSearchDirection;
import com.salon.enums.CustomerSearchType;

public interface ICustomerService {
    /**
     * Inserisce un nuovo cliente.
     *
     * @param customer il cliente da inserire
     * @return il cliente inserito
     */
    CustomerDto insert(CustomerDto customer);

    /**
     * Aggiorna un cliente esistente.
     *
     * @param customer il cliente da aggiornare
     * @return il cliente aggiornato
     */
    CustomerDto update(CustomerDto customer);

    /**
     * Elimina un cliente per ID.
     *
     * @param id l'ID del cliente da eliminare
     */

    void deleteById(Long id);

    /**
     * Trova un cliente per ID.
     *
     * @param id l'ID del cliente da trovare
     * @return il cliente trovato, se presente
     */
    CustomerDto findById(Long id);

    /**
     * Trova tutti i clienti.
     *
     * @return la lista di tutti i clienti
     */
    List<CustomerDto> findAll();

    /**
     * Trova clienti con paginazione.
     *
     * @param page    numero della pagina
     * @param size    dimensione della pagina
     * @param sortBy  tipo di campo per ordinamento
     * @param sortDir direzione dell'ordinamento
     * @return lista paginata dei clienti
     */
    List<CustomerDto> findByPagination(int page, int size, CustomerSearchType sortBy, CustomerSearchDirection sortDir);

    /**
     * Conta tutti i clienti.
     *
     * @return numero totale di clienti
     */
    long countAll();

    List<CustomerDto> findBySearch(CustomerSearchType type, String input);

}
