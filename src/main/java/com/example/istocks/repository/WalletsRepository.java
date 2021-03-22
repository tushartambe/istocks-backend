package com.example.istocks.repository;

import com.example.istocks.model.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletsRepository extends CrudRepository<Wallet, String> {

    public Wallet findByEmail(String email);

}
