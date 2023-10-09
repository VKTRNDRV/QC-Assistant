package com.example.qcassistant.repository.tag;

import com.example.qcassistant.domain.entity.tag.MedableTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedableTagRepository extends JpaRepository<MedableTag, Long> {
}
