package com.begr.escalade.config;

import com.begr.escalade.service.LuceneIndexService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
public class LuceneIndexConfig {

    @Bean
    public LuceneIndexService luceneIndexService(EntityManagerFactory EntityManagerFactory) {
        LuceneIndexService luceneIndexServiceBean = new LuceneIndexService(EntityManagerFactory);
        luceneIndexServiceBean.triggerIndexing();
        return luceneIndexServiceBean;
    }
}