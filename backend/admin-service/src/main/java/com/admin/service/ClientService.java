package com.admin.service;

import com.admin.entity.OAuthClient;
import com.admin.entity.OAuthScope;
import com.admin.repository.ClientRepository;
import com.admin.repository.ScopeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ScopeRepository scopeRepository;
    private final PasswordEncoder passwordEncoder;

    // ================= SCOPE =================

    public OAuthScope createScope(String name, String description) {
        OAuthScope scope = new OAuthScope();
        scope.setScopeName(name);
        scope.setDescription(description);
        return scopeRepository.save(scope);
    }

    public OAuthScope getScope(String name) {
        return scopeRepository.findByScopeName(name)
                .orElseThrow(() -> new RuntimeException("Scope not found"));
    }

    public List<OAuthScope> getAllScopes() {
        return scopeRepository.findAll();
    }

    public OAuthScope updateScope(String name, String description) {
        OAuthScope scope = getScope(name);
        scope.setDescription(description);
        return scopeRepository.save(scope);
    }

    public void deleteScope(String name) {
        OAuthScope scope = getScope(name);
        scopeRepository.delete(scope);
    }

    // ================= CLIENT =================

    public OAuthClient createClient(String clientId, String secret, Set<String> scopes) {

        OAuthClient client = new OAuthClient();
        client.setClientId(clientId);
        client.setClientSecret(passwordEncoder.encode(secret));

        Set<OAuthScope> scopeSet = new HashSet<>();
        scopes.forEach(scopeName ->
                scopeSet.add(getScope(scopeName)));

        client.setScopes(scopeSet);

        return clientRepository.save(client);
    }

    public OAuthClient getClient(String clientId) {
        return clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public List<OAuthClient> getAllClients() {
        return clientRepository.findAll();
    }

    public OAuthClient updateClient(String clientId, Set<String> scopes) {

        OAuthClient client = getClient(clientId);

        Set<OAuthScope> scopeSet = new HashSet<>();
        scopes.forEach(scopeName ->
                scopeSet.add(getScope(scopeName)));

        client.setScopes(scopeSet);

        return clientRepository.save(client);
    }

    public void deleteClient(String clientId) {
        OAuthClient client = getClient(clientId);
        clientRepository.delete(client);
    }
}