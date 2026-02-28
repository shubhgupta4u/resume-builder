package com.admin.repository;

import com.admin.entity.OAuthScope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScopeRepository extends JpaRepository<OAuthScope, String> {
    Optional<OAuthScope> findByScopeName(String scopeName);
}