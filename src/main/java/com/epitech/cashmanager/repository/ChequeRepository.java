package com.epitech.cashmanager.repository;

import com.epitech.cashmanager.model.Cheque;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChequeRepository extends CrudRepository<Cheque, Integer> {

    // @Query("select c from Cheque c where c.qrCode=:qrCode")
    // Cheque findByQrCode(@Param("qrCode") String qrCode);

    Cheque findFirstByQrCode(String qrCode);
}
