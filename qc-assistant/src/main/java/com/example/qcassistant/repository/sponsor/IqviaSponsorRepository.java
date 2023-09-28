package com.example.qcassistant.repository.sponsor;

import com.example.qcassistant.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistant.domain.entity.sponsor.MedidataSponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IqviaSponsorRepository extends JpaRepository<IqviaSponsor, Long> {

    Optional<IqviaSponsor> findFirstByName(String name);

    List<IqviaSponsor> findAllByNameNot(String UNKNOWN);
}
