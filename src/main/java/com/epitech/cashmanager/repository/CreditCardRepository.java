package com.epitech.cashmanager.repository;

import com.epitech.cashmanager.model.CreditCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CreditCardRepository extends CrudRepository<CreditCard, Integer> {
    CreditCard findFirstByNfcId(String nfcId);
}
