package com.example.saaca.syncup.dao;

import com.example.saaca.syncup.model.ReturnCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnCredentialsRepository extends JpaRepository<ReturnCredentials, Integer> {

    @Query("select r from ReturnCredentials r where r.assessmentYear = :assessmentYear and r.id = :id")
    ReturnCredentials[] findById(final String assessmentYear, final int id);
}
