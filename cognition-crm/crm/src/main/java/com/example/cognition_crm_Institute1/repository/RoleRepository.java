package com.example.cognition_crm_Institute1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cognition_crm_Institute1.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}