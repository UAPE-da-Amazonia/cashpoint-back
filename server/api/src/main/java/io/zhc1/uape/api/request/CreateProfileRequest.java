package io.zhc1.uape.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateProfileRequest(
        String name,
        String email,
        String phone,
        String document,
        String address,
        @JsonProperty("profile_type") String profileType) {}
