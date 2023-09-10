package com.example.qcassistant.repository.sponsor;

import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedidataSponsorRepository extends JpaRepository<MedidataSponsor, Long> {

    Optional<MedidataSponsor> findFirstByName(String name);
}
