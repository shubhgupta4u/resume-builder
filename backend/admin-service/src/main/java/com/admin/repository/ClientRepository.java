package com.admin.repository;

import com.admin.entity.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<OAuthClient, String> {
    Optional<OAuthClient> findByClientId(String clientId);
}