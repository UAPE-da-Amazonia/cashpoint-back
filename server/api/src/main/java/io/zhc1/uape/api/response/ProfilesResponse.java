package io.zhc1.uape.api.response;

import java.util.List;

import io.zhc1.uape.model.Profile;

public record ProfilesResponse(List<ProfileResponse> profiles) {
    public static ProfilesResponse from(List<Profile> profiles) {
        List<ProfileResponse> profileResponses =
                profiles.stream().map(ProfileResponse::from).toList();
        return new ProfilesResponse(profileResponses);
    }
}
