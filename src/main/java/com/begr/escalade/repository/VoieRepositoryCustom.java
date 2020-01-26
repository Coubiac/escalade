package com.begr.escalade.repository;

import com.begr.escalade.entity.Voie;

import java.util.List;

public interface VoieRepositoryCustom {

    List<Voie> findAllBySecteurId(Long secteurId);
}
