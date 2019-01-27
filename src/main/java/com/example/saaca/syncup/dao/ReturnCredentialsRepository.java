package com.example.saaca.syncup.dao;

import com.example.saaca.syncup.model.ReturnCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnCredentialsRepository extends JpaRepository<ReturnCredentials, Integer> {
}
