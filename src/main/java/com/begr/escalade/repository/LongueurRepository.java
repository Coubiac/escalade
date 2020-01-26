package com.begr.escalade.repository;

import com.begr.escalade.entity.Longueur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LongueurRepository extends JpaRepository<Longueur, Long>, LongueurRepositoryCustom {
}
