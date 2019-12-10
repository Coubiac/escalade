package com.begr.escalade.repository;

import com.begr.escalade.entity.Secteur;
import com.begr.escalade.entity.Site;

import java.util.List;

public interface SecteurRepositoryCustom {

    List<Secteur> findAllBySiteId(Long siteId);
}
