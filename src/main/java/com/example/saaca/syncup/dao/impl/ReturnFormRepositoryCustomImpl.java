package com.example.saaca.syncup.dao.impl;

import com.example.saaca.syncup.dao.ReturnFormRepositoryCustom;
import com.example.saaca.syncup.model.ReturnForm;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

@Repository
public class ReturnFormRepositoryCustomImpl implements ReturnFormRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ReturnForm> findByReturnType(String returnType) {
        Query query = entityManager.createNativeQuery("select * from return_forms where return_type = ?", ReturnForm.class);
        query.setParameter(1, returnType);
        return query.getResultList();
    }

    @Override
    public ReturnForm findByReturnTypeAndReturnForm(String returnType, String returnFormName) {
        Query query = entityManager.createNativeQuery("select * from return_forms where form_name = ? and return_type = ?", ReturnForm.class);
        query.setParameter(1, returnFormName);
        query.setParameter(2, returnType);
        List<ReturnForm> resultList = query.getResultList();
        if (resultList == null || resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
    }
}
