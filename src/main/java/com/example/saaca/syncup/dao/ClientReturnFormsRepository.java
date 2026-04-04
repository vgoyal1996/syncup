package com.example.saaca.syncup.dao;

import com.example.saaca.syncup.model.ClientReturnForms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientReturnFormsRepository extends JpaRepository<ClientReturnForms, Integer> {
}
