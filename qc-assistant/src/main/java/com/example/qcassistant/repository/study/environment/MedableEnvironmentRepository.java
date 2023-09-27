package com.example.qcassistant.repository.study.environment;

import com.example.qcassistant.domain.entity.study.environment.MedableEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedableEnvironmentRepository extends JpaRepository<MedableEnvironment, Long> {

}
