package com.dating.authentication.controller;

import com.dating.authentication.common.MessageTitles;
import com.dating.authentication.data.MessageResponseData;
import com.dating.authentication.data.UserRegistrationCredentialsData;
import com.dating.authentication.data.UserSingInCredentialsData;
import com.dating.authentication.service.CredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0.0.1/authentication")
@AllArgsConstructor
public class AuthenticationController {
    private CredentialsService credentialsService;

    @PostMapping("/login")
    public MessageResponseData login(@RequestBody UserSingInCredentialsData data) {
        boolean isCredentialsExists;
        boolean isMail = false;

        if (data.getLogin().contains("@")) {
            isCredentialsExists = credentialsService.checkUserExists(data.getLogin(), true);
            isMail = true;
        } else {
            isCredentialsExists = credentialsService.checkUserExists(data.getLogin(), false);
        }

        if (!isCredentialsExists) {
            return new MessageResponseData(MessageTitles.ERROR.name(), "There is no user with this login!");
        }

        if (!credentialsService.validateCredentials(data.getLogin(), data.getPassword(), isMail)) {
            return new MessageResponseData(MessageTitles.ERROR.name(), "User data not matches!");
        }

        return new MessageResponseData(MessageTitles.SUCCESS.name(), "User data matches!");
    }

    @PutMapping("/registration")
    public MessageResponseData registration(@RequestBody UserRegistrationCredentialsData data) {
        if (credentialsService.checkUserExists(data.getUserName(), false)) {
            return new MessageResponseData(MessageTitles.ERROR.name(), "Account with this login already exists!");
        }

        if (credentialsService.checkUserExists(data.getMail(), true)) {
            return new MessageResponseData(MessageTitles.ERROR.name(), "Account with this mail already exists!");
        }

        credentialsService.addCredentials(data.getUserName(), data.getMail(), data.getPassword());

        return new MessageResponseData(MessageTitles.SUCCESS.name(), "User data matches!");
    }
}
