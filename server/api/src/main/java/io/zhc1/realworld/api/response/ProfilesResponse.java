package io.zhc1.realworld.api.response;

import java.util.List;

import io.zhc1.realworld.model.Profile;

public record ProfilesResponse(List<ProfileResponse> profiles) {
    public static ProfilesResponse from(List<Profile> profiles) {
        List<ProfileResponse> profileResponses =
                profiles.stream().map(ProfileResponse::from).toList();
        return new ProfilesResponse(profileResponses);
    }
}
