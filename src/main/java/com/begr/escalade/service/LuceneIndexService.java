package com.begr.escalade.service;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;

public class LuceneIndexService {
    private final Logger logger = (Logger) LoggerFactory.getLogger(LuceneIndexService.class);

    private FullTextEntityManager fullTextEntityManager;

    public LuceneIndexService(EntityManagerFactory entityManagerFactory){
        fullTextEntityManager = Search.getFullTextEntityManager(entityManagerFactory.createEntityManager());
    }

    public void triggerIndexing() {
        try {
            logger.debug("=====   DEBUT INDEXATION   =====");
            fullTextEntityManager.createIndexer().startAndWait();
            logger.debug("=====   INDEX TERMINÉ   =====");
        } catch (InterruptedException e) {
            logger.warn("INDEX ERREUR");
            throw new RuntimeException(e);

        }
    }
}
