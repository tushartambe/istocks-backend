package com.example.istocks.repository;

import com.example.istocks.model.IStocksUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStocksUserRepository extends CrudRepository<IStocksUser, String> {

    public IStocksUser findByEmail(String email);

}
