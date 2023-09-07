package com.example.qcassistant.repository;

import com.example.qcassistant.domain.entity.destination.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findFirstByName(String name);
}
