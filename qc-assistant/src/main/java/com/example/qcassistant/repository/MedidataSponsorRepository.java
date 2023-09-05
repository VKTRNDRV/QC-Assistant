package com.example.qcassistant.repository;

import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedidataSponsorRepository extends JpaRepository<MedidataSponsor, Long> {

}
