package com.census.support.batchJob.tablet;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TabletRepository extends JpaRepository<Tablet, Long>, JpaSpecificationExecutor<Tablet> {
}
