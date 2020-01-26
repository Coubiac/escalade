package com.begr.escalade.repository;

import com.begr.escalade.entity.Voie;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class VoieRepositoryImpl implements VoieRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Voie> findAllBySecteurId(Long secteurId) {
        Query query = entityManager.createNativeQuery("SELECT * FROM voies WHERE secteur_id = ?", Voie.class);
        query.setParameter(1, secteurId);
        return query.getResultList();
    }
}
