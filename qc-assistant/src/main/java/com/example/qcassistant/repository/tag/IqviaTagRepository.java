package com.example.qcassistant.repository.tag;

import com.example.qcassistant.domain.entity.tag.IqviaTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IqviaTagRepository extends JpaRepository<IqviaTag, Long> {

}
