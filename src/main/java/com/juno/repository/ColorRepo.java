package com.juno.repository;

import com.juno.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepo extends JpaRepository<Color, Long> {
    Optional<Color> findByName(String name);
}
