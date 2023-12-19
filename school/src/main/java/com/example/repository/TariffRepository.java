package com.example.repository;

import com.example.entity.Tariff;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff,Integer> {
    List<Tariff> findAllByActiveAndDelete(boolean active, boolean delete, Sort sort);
    List<Tariff> findAllByDelete(boolean delete,Sort sort);

    Optional<Tariff> findByIdAndDeleteFalse(Integer id);
}
