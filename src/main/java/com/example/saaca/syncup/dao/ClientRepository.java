package com.example.saaca.syncup.dao;

import com.example.saaca.syncup.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Modifying
    @Query("delete from Client c where c.clientCode in ?1")
    int deleteClientsByClientCodes(List<String> clientCodeList);

    Client findByClientCode(String clientCode);

}
