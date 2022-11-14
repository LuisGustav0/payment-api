package com.ead.model.response.users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEventResponse {

    private UUID id;
    private String login;
    private String email;
    private String fullName;
    private String statusE;
    private String typeE;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;

    private String actionTypeE;

    private String createdAt;
    private String updatedAt;
}
