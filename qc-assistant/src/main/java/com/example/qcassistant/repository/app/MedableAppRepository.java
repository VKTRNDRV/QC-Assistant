package com.example.qcassistant.repository.app;

import com.example.qcassistant.domain.entity.app.MedableApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedableAppRepository extends JpaRepository<MedableApp, Long> {

    Optional<MedableApp> findFirstByName(String name);
}
