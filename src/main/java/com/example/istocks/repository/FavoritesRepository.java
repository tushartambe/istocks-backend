package com.example.istocks.repository;

import com.example.istocks.model.FavoriteStock;
import com.example.istocks.model.IStocksUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends CrudRepository<FavoriteStock, String> {

    public List<FavoriteStock> findByEmail(String email);

}
