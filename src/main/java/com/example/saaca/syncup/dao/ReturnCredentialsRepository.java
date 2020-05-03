package com.example.saaca.syncup.dao;

import com.example.saaca.syncup.model.ReturnCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnCredentialsRepository extends JpaRepository<ReturnCredentials, Integer> {

    @Query("select r from ReturnCredentials r where r.assessmentYear = :assessmentYear and r.client.id = :id")
    List<ReturnCredentials> findByAssessmentYearAndId(final String assessmentYear, final int id);

    ReturnCredentials findByAssessmentYearAndReturnId(final String assessmentYear, final int returnId);
}
