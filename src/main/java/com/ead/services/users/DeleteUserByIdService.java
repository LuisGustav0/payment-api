package com.ead.services.users;

import com.ead.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserByIdService {

    private final UserRepository repository;

    @Transactional
    public void call(final UUID id) {
        this.repository.deleteById(id);
    }
}
