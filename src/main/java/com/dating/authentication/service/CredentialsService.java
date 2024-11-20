package com.dating.authentication.service;

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

    public boolean checkUserExists(String login, boolean isMail) {
        if (isMail) return credentialsRepository.existsByUserMail(login);

        return credentialsRepository.existsByUserName(login);
    }

    public boolean validateCredentials(String login, String password, boolean isMail) {
        UserCredentialsModel credentials;

        if (isMail) {
            credentials = credentialsRepository.getByUserMail(login);
        } else {
            credentials = credentialsRepository.getByUserName(login);
        }

        return passwordEncoder.matches(password, credentials.getUserPassword());
    }

    public boolean addCredentials(String userName, String mail, String password) {
        if (checkUserExists(userName, false)) return false;

        UserCredentialsModel credentialsModel = new UserCredentialsModel();

        credentialsModel.setUserName(userName);
        credentialsModel.setUserMail(mail);
        credentialsModel.setUserPassword(passwordEncoder.encode(password));

        credentialsRepository.save(credentialsModel);

        return true;
    }
}
