package com.ead.assembler.users;

import com.ead.enums.UserStatusE;
import com.ead.enums.UserTypeE;
import com.ead.model.UserModel;
import com.ead.model.response.users.UserEventResponse;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class UserEventResponseAssembler {

    public UserModel toModel(final UserEventResponse response) {
        final UserStatusE statusE = UserStatusE.valueOf(response.getStatusE());
        final UserTypeE typeE = UserTypeE.valueOf(response.getTypeE());
        final var createdAt = OffsetDateTime.parse(response.getCreatedAt());
        final var updatedAt = OffsetDateTime.parse(response.getUpdatedAt());

        return UserModel.builder()
                        .id(response.getId())
                        .email(response.getEmail())
                        .fullName(response.getFullName())
                        .statusE(statusE)
                        .typeE(typeE)
                        .cpf(response.getCpf())
                        .createdAt(createdAt)
                        .updatedAt(updatedAt)
                        .build();
    }
}
