package com.salon.customer;

import java.util.List;

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
     * Trova un cliente per nome o cognome.
     *
     * @param id l'ID del cliente da trovare
     * @return il cliente trovato, se presente
     */
    List<CustomerDto> findByNameOrSurname(String input);

}
