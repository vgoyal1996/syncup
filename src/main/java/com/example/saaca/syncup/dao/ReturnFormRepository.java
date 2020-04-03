package com.example.saaca.syncup.dao;

import com.example.saaca.syncup.model.ReturnForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnFormRepository extends JpaRepository<ReturnForm, Integer>, ReturnFormRepositoryCustom {

    @Modifying
    @Query("delete from ReturnForm r where r.formName in ?1")
    int deleteReturnFormsWithFormNames(List<String> formNameList);
}
