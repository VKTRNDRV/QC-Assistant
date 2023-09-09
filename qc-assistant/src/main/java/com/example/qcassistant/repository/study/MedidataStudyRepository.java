package com.example.qcassistant.repository.study;

import com.example.qcassistant.domain.entity.study.MedidataStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedidataStudyRepository extends JpaRepository<MedidataStudy, Long> {

}
