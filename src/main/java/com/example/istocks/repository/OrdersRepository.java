package com.example.istocks.repository;

import com.example.istocks.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends CrudRepository<Order, String> {

    public List<Order> findByEmail(String email);

    public Order findByTransactionId(String transactionId);

}
