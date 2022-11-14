package com.ead.repositories;

import com.ead.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>,
        JpaSpecificationExecutor<UserModel> {

    @Query("SELECT " +
            "CASE " +
            "   WHEN COUNT(user) > 0 THEN " +
            "       false " +
            "   ELSE " +
            "       true " +
            "END " +
            "FROM UserModel user " +
            "WHERE (" +
            "   user.typeE = 'INSTRUCTOR' OR" +
            "   user.typeE = 'ADMIN'" +
            ") AND user.id = :id"
    )
    boolean isNotExistsTypeInstructorOrAdminById(final UUID id);
}
