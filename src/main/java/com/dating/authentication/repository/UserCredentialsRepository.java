package com.dating.authentication.repository;

import com.dating.authentication.model.UserCredentialsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentialsModel, Integer> {
    UserCredentialsModel getByUserId(int id);
    UserCredentialsModel getByUserName(String userName);
    UserCredentialsModel getByUserMail(String mail);
    boolean existsByUserName(String userName);
    boolean existsByUserMail(String mail);
}
