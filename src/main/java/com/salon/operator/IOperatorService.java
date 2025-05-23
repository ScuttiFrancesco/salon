package com.salon.operator;

import java.util.List;

public interface IOperatorService {
    /**
     * Inserisce un nuovo operatore.
     *
     * @param operator l'operatore da inserire
     * @return l'operatore inserito
     */
    OperatorDto insert(OperatorDto operator);

    /**
     * Aggiorna un operatore esistente.
     *
     * @param operator l'operatore da aggiornare
     * @return l'operatore aggiornato
     */
    OperatorDto update(OperatorDto operator);

    /**
     * Elimina un operatore per ID.
     *
     * @param id l'ID dell'operatore da eliminare
     */
    void deleteById(Long id);

    /**
     * Trova un operatore per ID.
     *
     * @param id l'ID dell'operatore da trovare
     * @return l'operatore trovato, se presente
     */
    OperatorDto findById(Long id);

    /**
     * Trova tutti gli operatori.
     *
     * @return la lista di tutti gli operatori
     */
    List<OperatorDto> findAll();

    /**
     * Trova operatori per nome o cognome.
     *
     * @param input stringa di ricerca per nome o cognome
     * @return lista degli operatori trovati
     */
    List<OperatorDto> findByNameOrSurname(String input);
    
    /**
     * Trova operatori per email.
     *
     * @param input stringa di ricerca per email
     * @return lista degli operatori trovati
     */
    List<OperatorDto> findByEmail(String input);
    
    /**
     * Trova operatori per numero di telefono.
     *
     * @param input stringa di ricerca per numero di telefono
     * @return lista degli operatori trovati
     */
    List<OperatorDto> findByPhoneNumber(String input);
    
    /**
     * Trova operatori per posizione.
     *
     * @param position stringa di ricerca per posizione
     * @return lista degli operatori trovati
     */
    List<OperatorDto> findByPosition(String position);
    
    /**
     * Trova operatori per livello.
     *
     * @param level livello da cercare
     * @return lista degli operatori trovati
     */
    List<OperatorDto> findByLevel(int level);
}
