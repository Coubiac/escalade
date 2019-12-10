package com.begr.escalade.repository;

import com.begr.escalade.entity.Secteur;
import com.begr.escalade.entity.Site;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SecteurRepositoryImpl implements SecteurRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Secteur> findAllBySiteId(Long siteId) {
        Query query = entityManager.createNativeQuery("SELECT * FROM secteurs WHERE site_id = ?", Secteur.class);
        query.setParameter(1, siteId);
        return query.getResultList();
    }
}
