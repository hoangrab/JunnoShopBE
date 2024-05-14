package com.juno.repository;

import com.juno.entity.SizeOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeOptionRepo extends JpaRepository<SizeOption, Long> {
    Optional<SizeOption> findByName(String name);
}
