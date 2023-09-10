package com.example.qcassistant.repository.auth;

import com.example.qcassistant.domain.entity.auth.RoleEntity;
import com.example.qcassistant.domain.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findRoleEntityByRole(RoleEnum role);
}
