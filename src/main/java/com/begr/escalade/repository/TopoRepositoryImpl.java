package com.begr.escalade.repository;

import com.begr.escalade.entity.Secteur;
import com.begr.escalade.entity.Topo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TopoRepositoryImpl implements TopoRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Topo> findAllBySiteId(Integer siteId) {
        Query query = entityManager.createNativeQuery("SELECT * FROM topos WHERE site_id = ?", Topo.class);
        query.setParameter(1, siteId);
        return query.getResultList();
    }

    @Override
    public List<Topo> findAllDispo() {
        Query query = entityManager.createNativeQuery("SELECT * FROM topos WHERE borrowed = false", Topo.class);
        return query.getResultList();
    }


    @Override
    public List<Topo> findAllByOwnerId(Integer ownerId) {
        Query query = entityManager.createNativeQuery("SELECT * FROM topos WHERE owner_id = ?", Topo.class);
        query.setParameter(1, ownerId);
        return query.getResultList();
    }
}
