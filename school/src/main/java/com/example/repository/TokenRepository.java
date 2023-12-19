package com.example.repository;


import com.example.entity.FireBaseToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<FireBaseToken, Integer> {
}
