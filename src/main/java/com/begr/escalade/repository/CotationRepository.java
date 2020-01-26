package com.begr.escalade.repository;

import com.begr.escalade.entity.Cotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotationRepository extends JpaRepository<Cotation, Long> {
}
