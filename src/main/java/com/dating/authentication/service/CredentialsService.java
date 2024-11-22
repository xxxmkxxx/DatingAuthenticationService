package com.dating.authentication.service;

import com.dating.authentication.data.ResponseData;
import com.dating.authentication.data.UserRegistrationCredentialsData;
import com.dating.authentication.data.UserSingInCredentialsData;
import com.dating.authentication.model.UserCredentialsModel;
import com.dating.authentication.repository.UserCredentialsRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CredentialsService {
    private UserCredentialsRepository credentialsRepository;
    private PasswordEncoder passwordEncoder;

    private boolean checkUserExists(String login) {
        if (login.contains("@")) return credentialsRepository.existsByUserMail(login);

        return credentialsRepository.existsByUserName(login);
    }

    public ResponseData<Void> validateCredentials(UserSingInCredentialsData data) {
        UserCredentialsModel credentials;
        if (checkUserExists(data.getLogin())) {
            credentials = credentialsRepository.getByUserMail(data.getLogin());
        } else {
            credentials = credentialsRepository.getByUserName(data.getLogin());
        }

        if (credentials == null) {
            return new ResponseData<>(false, "There is no user with this login!", null);
        }

        if (passwordEncoder.matches(data.getPassword(), credentials.getUserPassword())) {
            return new ResponseData<>(true, "User data matches!", null);
        } else {
            return new ResponseData<>(true, "User data not matches!", null);
        }
    }

    public ResponseData<Void> addCredentials(UserRegistrationCredentialsData data) {
        if (checkUserExists(data.getUserName())) {
            return new ResponseData<>(false, "Account with this login already exists!", null);
        }

        if (checkUserExists(data.getMail())) {
            return new ResponseData<>(false, "Account with this mail already exists!", null);
        }

        UserCredentialsModel credentialsModel = new UserCredentialsModel();

        credentialsModel.setUserName(data.getUserName());
        credentialsModel.setUserMail(data.getMail());
        credentialsModel.setUserPassword(passwordEncoder.encode(data.getPassword()));

        credentialsRepository.save(credentialsModel);

        return new ResponseData<>(true, "registration was successful!", null);
    }
}
