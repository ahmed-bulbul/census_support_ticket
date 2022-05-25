package com.census.support.batchJob.tablet;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TabletRepository extends JpaRepository<Tablet, Long>, JpaSpecificationExecutor<Tablet> {


    Tablet findBySimNo(String searchItem);



    @Query(value = "SELECT * FROM tablet_info WHERE bar_code like %?1%", nativeQuery = true)
    Tablet getByBarCode(String barCode);
}
