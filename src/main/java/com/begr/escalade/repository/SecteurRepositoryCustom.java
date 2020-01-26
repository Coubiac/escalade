package com.begr.escalade.repository;

import com.begr.escalade.entity.Secteur;

import java.util.List;

public interface SecteurRepositoryCustom {

    List<Secteur> findAllBySiteId(Long siteId);
}
