package com.example.saaca.syncup.dao;

import com.example.saaca.syncup.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
    Login findByUserId(String userId);
}
