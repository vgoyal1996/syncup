package com.example.saaca.syncup.dao;

import com.example.saaca.syncup.model.ReturnForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnFormRepository extends JpaRepository<ReturnForm, Integer>, ReturnFormRepositoryCustom {
}
