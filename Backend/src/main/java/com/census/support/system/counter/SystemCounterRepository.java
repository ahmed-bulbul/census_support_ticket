package com.census.support.system.counter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemCounterRepository extends JpaRepository<SystemCounter, Long> {

    Optional<SystemCounter> getByCode(String code);
    SystemCounter findByCode(String code);
    SystemCounter findByCodeAndCounterName(String code, String counterName);

}
