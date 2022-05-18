package com.census.support.batchJob.tablet;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface TabletRepository extends JpaRepository<Tablet, Long>, JpaSpecificationExecutor<Tablet> {


    Tablet findBySimNo(String searchItem);

    @Query(value = "SELECT * FROM TABLET_INFO WHERE BAR_CODE = ?1", nativeQuery = true)
    Tablet findByBarCode(String barCode);

    Tablet getByBarCode(String barCode);
}
