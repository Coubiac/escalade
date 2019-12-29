package com.begr.escalade.repository;

import com.begr.escalade.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long>, SiteRepositoryCustom {

}
