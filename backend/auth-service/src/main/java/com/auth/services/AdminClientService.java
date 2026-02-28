package com.auth.services;

import com.auth.models.ClientDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AdminClientService {

    private final InternalTokenGenerator tokenGenerator;
    private final RestTemplate restTemplate;

    @Value("${auth.admin-service.base-url}")
    private String adminBaseUrl;

    public ClientDTO loadClient(String clientId) {

        String token = tokenGenerator.generateSystemToken();
        System.out.println("Generated internal token: " + token);
        System.out.println("Looking for client: " + clientId);
        String url = adminBaseUrl + "/api/auth/client/" + clientId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ClientDTO> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        ClientDTO.class
                );

        return response.getBody();
    }
}