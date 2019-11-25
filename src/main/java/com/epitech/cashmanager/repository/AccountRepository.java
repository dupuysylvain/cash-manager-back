package com.epitech.cashmanager.repository;

import com.epitech.cashmanager.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
}
