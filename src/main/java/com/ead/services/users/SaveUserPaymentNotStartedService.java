package com.ead.services.users;

import com.ead.enums.PaymentStatusE;
import com.ead.model.UserModel;
import com.ead.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveUserPaymentNotStartedService {

    private final UserRepository repository;

    public UserModel call(final UserModel user) {
        user.setPaymentStatusE(PaymentStatusE.NOT_STARTED);

        return this.repository.save(user);
    }
}
