package com.census.support.system.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VisitorRepository extends JpaRepository<Visitor, Long>, JpaSpecificationExecutor<Visitor> {

}