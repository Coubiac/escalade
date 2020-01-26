package com.begr.escalade.config;

import com.begr.escalade.service.LuceneIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class LuceneIndexConfig {
    private final Logger logger = LoggerFactory.getLogger(LuceneIndexConfig.class);


    @Bean
    public LuceneIndexService luceneIndexService(EntityManagerFactory EntityManagerFactory) {
        logger.debug("=====   DEBUT CONFIG LUCENE INDEX   =====");
        LuceneIndexService luceneIndexServiceBean = new LuceneIndexService(EntityManagerFactory);
        luceneIndexServiceBean.triggerIndexing();
        return luceneIndexServiceBean;
    }
}