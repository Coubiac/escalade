package com.begr.escalade.repository;

import com.begr.escalade.entity.Longueur;

import java.util.List;

public interface LongueurRepositoryCustom {

   List<Longueur> findAllByVoieId(Long longueurId);
}
