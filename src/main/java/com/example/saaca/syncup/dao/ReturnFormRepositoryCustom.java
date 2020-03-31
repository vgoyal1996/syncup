package com.example.saaca.syncup.dao;

import com.example.saaca.syncup.model.ReturnForm;

import java.util.List;

public interface ReturnFormRepositoryCustom {

    List<ReturnForm> findByReturnType(String returnType);

}
