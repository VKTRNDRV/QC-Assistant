package com.example.qcassistant.repository.tag;

import com.example.qcassistant.domain.entity.tag.MedidataTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedidataTagRepository extends JpaRepository<MedidataTag, Long> {

}
