package com.example.istocks.repository;

import com.example.istocks.model.Holding;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoldingRepository extends CrudRepository<Holding, String> {

    public List<Holding> findByEmail(String email);

    public Holding findByEmailAndSymbol(String email, String symbol);

}
