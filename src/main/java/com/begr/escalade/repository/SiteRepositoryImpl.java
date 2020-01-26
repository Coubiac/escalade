package com.begr.escalade.repository;


import com.begr.escalade.entity.Site;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

@Repository
public class SiteRepositoryImpl implements SiteRepositoryCustom{
    private final Logger logger = (Logger) LoggerFactory.getLogger(SiteRepositoryImpl.class);

    @Autowired
    EntityManagerFactory emf;

    @Override
    @Transactional
    public List<Site> findFulltext(String searchQuery) {
        EntityManager entityManager = emf.createEntityManager();
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
        entityManager.getTransaction().begin();

        QueryBuilder qb = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder().forEntity(Site.class).get();
        logger.debug("RECHERCHE DES MOTS SUIVANTS: " + searchQuery);
        org.apache.lucene.search.Query luceneQuery = qb
                .keyword()
                .onFields("name",
                        "description",
                        "secteurs.name",
                        "secteurs.description",
                        "secteurs.voies.name",
                        "secteurs.voies.description",
                        "secteurs.voies.cotation.value",
                        "secteurs.voies.longueurs.name",
                        "secteurs.voies.longueurs.description",
                        "secteurs.voies.longueurs.cotation.value")
                .matching(searchQuery)
                .createQuery();

        javax.persistence.Query jpaQuery =
                fullTextEntityManager.createFullTextQuery(luceneQuery, Site.class);

        List result = jpaQuery.getResultList();
        logger.debug("RÉSULTAT DE RECHERCHE: " + result.toString());
        return result;
    }
}
