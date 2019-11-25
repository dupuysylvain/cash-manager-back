package com.epitech.cashmanager.repository;

import com.epitech.cashmanager.model.CreditCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CreditCardRepository extends CrudRepository<CreditCard, Integer> {

    // @Query("select cc from CreditCard cc where cc.nfcId=:nfcId")
    // CreditCard findByNfcId(@Param("nfcId") String nfcId);

    CreditCard findFirstByNfcId(String nfcId);
}
