package com.auth.models;

import java.util.Set;

public record ClientDTO(
        String id,
        String clientId,
        String clientSecret,
        Set<String> scopes
) {}