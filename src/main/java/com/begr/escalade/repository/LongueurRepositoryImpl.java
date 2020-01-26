package com.begr.escalade.repository;

import com.begr.escalade.entity.Longueur;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class LongueurRepositoryImpl implements LongueurRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Longueur> findAllByVoieId(Long voieId) {
        Query query = entityManager.createNativeQuery("SELECT * FROM longueurs WHERE voie_id = ?", Longueur.class);
        query.setParameter(1, voieId);
        return query.getResultList();
    }
}
