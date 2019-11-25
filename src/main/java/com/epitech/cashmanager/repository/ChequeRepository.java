package com.epitech.cashmanager.repository;

import com.epitech.cashmanager.model.Cheque;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChequeRepository extends CrudRepository<Cheque, Integer> {
    Cheque findFirstByQrCode(String qrCode);
}
