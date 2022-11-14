package com.ead.services.users;

import com.ead.model.UserModel;
import com.ead.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveUserService {

    private final UserRepository repository;

    public UserModel call(final UserModel user) {
        return this.repository.save(user);
    }
}
