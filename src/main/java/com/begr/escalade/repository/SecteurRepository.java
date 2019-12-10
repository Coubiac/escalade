package com.begr.escalade.repository;

import com.begr.escalade.entity.Secteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long>, SecteurRepositoryCustom {

}
