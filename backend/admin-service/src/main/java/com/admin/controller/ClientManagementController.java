package com.admin.controller;

import com.admin.models.ClientDTO;
import com.admin.entity.OAuthClient;
import com.admin.entity.OAuthScope;
import com.admin.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientManagementController {

    private final ClientService clientService;

    // ================= SCOPE APIs =================

    @PreAuthorize("hasAuthority('SCOPE_scope:read') or hasAuthority('SCOPE_scope:all')")
    @GetMapping("/scopes")
    public List<OAuthScope> getAllScopes() {
        return clientService.getAllScopes();
    }

    @PreAuthorize("hasAuthority('SCOPE_scope:read') or hasAuthority('SCOPE_scope:all')")
    @GetMapping("/scope/{scopeName}")
    public OAuthScope getScope(@PathVariable String scopeName) {
        return clientService.getScope(scopeName);
    }

    @PreAuthorize("hasAuthority('SCOPE_scope:create') or hasAuthority('SCOPE_scope:all')")
    @PostMapping("/scope")
    public OAuthScope createScope(
            @RequestParam String name,
            @RequestParam String description) {

        return clientService.createScope(name, description);
    }

    @PreAuthorize("hasAuthority('SCOPE_scope:update') or hasAuthority('SCOPE_scope:all')")
    @PutMapping("/scope/{scopeName}")
    public OAuthScope updateScope(
            @PathVariable String scopeName,
            @RequestParam String description) {

        return clientService.updateScope(scopeName, description);
    }

    @PreAuthorize("hasAuthority('SCOPE_scope:delete') or hasAuthority('SCOPE_scope:all')")
    @DeleteMapping("/scope/{scopeName}")
    public void deleteScope(@PathVariable String scopeName) {
        clientService.deleteScope(scopeName);
    }

    // ================= CLIENT APIs =================

    @PreAuthorize("hasAuthority('SCOPE_client:create') or hasAuthority('SCOPE_client:all')")
    @PostMapping("/client")
    public OAuthClient createClient(
            @RequestParam String clientId,
            @RequestParam String secret,
            @RequestParam Set<String> scopes) {

        return clientService.createClient(clientId, secret, scopes);
    }

    @PreAuthorize("hasAuthority('SCOPE_client:read') or hasAuthority('SCOPE_client:all')")
    @GetMapping("/client/{clientId}")
    public OAuthClient getClient(@PathVariable String clientId) {
        return clientService.getClient(clientId);
    }

    @PreAuthorize("hasAuthority('SCOPE_client:read') or hasAuthority('SCOPE_client:all')")
    @GetMapping("/clients")
    public List<OAuthClient> getAllClients() {
        return clientService.getAllClients();
    }

    @PreAuthorize("hasAuthority('SCOPE_client:update') or hasAuthority('SCOPE_client:all')")
    @PutMapping("/client/{clientId}")
    public OAuthClient updateClient(
            @PathVariable String clientId,
            @RequestParam Set<String> scopes) {

        return clientService.updateClient(clientId, scopes);
    }

    @PreAuthorize("hasAuthority('SCOPE_client:delete') or hasAuthority('SCOPE_client:all')")
    @DeleteMapping("/client/{clientId}")
    public void deleteClient(@PathVariable String clientId) {
        clientService.deleteClient(clientId);
    }

    @PreAuthorize("hasAuthority('SCOPE_client:read') or hasAuthority('SCOPE_client:all') or hasAuthority('SCOPE_client:internal-read')")
    @GetMapping("/auth/client/{clientId}")
    public ClientDTO getClientForAuth(@PathVariable String clientId) {

        OAuthClient client = clientService.getClient(clientId);

        return new ClientDTO(
                client.getId(),
                client.getClientId(),
                client.getClientSecret(),
                client.getScopes()
                      .stream()
                      .map(OAuthScope::getScopeName)
                      .collect(Collectors.toSet())
        );
    }
}