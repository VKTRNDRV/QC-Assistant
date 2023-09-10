package com.example.qcassistant.repository.study.environment;

import com.example.qcassistant.domain.entity.study.environment.MedidataEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedidataEnvironmentRepository extends JpaRepository<MedidataEnvironment, Long> {

}
