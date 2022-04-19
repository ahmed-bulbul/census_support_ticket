package com.census.support.system.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SystemMenuRepository  extends JpaRepository<SystemMenu, Long> , JpaSpecificationExecutor<SystemMenu> {
    List<SystemMenu> findAllByParentMenu(SystemMenu systemMenu); // pass systemMenu null
    List<SystemMenu> getAllByParentMenuIsNull();
    List<SystemMenu> getAllByLeftSideMenuAndIsActiveAndParentMenuIsNullOrderBySequenceAsc(Boolean lm, Boolean ac);

    List<SystemMenu> getAllByIsActiveOrderBySequenceAsc(Boolean ac);
    List<SystemMenu> getAllByIsActiveAndParentMenuIsNullOrderBySequenceAsc(Boolean ac);
    List<SystemMenu> getAllByIsActiveAndParentMenuOrderBySequenceAsc(Boolean ac, SystemMenu systemMenu);
    Integer countByParentMenuAndIsActive(SystemMenu systemMenu, Boolean ac);


    // @Query("select case when count(c)> 0 then true else false end from SystemMenu c where lower(c.code) like lower(:model)") // save like for oracle - ignore case
    @Query("select case when count(c)> 0 then true else false end from SystemMenu c where c.code = :code")
    boolean existsByMenuCodeCheck(@Param("code") String code);
    boolean existsByCode(String code);

    SystemMenu getByCode(String code);


    Optional<Object> findByCode(String dashboard);
}
