package com.example.istocks.repository;

import com.example.istocks.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends CrudRepository<Transaction, String> {

    public List<Transaction> findByEmail(String email);

}
