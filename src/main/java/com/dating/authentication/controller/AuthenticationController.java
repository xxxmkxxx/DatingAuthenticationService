package com.dating.authentication.controller;

import com.dating.authentication.data.ResponseData;
import com.dating.authentication.data.UserRegistrationCredentialsData;
import com.dating.authentication.data.UserSingInCredentialsData;
import com.dating.authentication.service.CredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0.0.1/authentication")
@AllArgsConstructor
public class AuthenticationController {
    private CredentialsService credentialsService;

    @PostMapping("/login")
    public ResponseEntity<ResponseData<Object>> login(@RequestBody UserSingInCredentialsData data) {
        boolean isCredentialsExists;
        boolean isMail = false;

        if (data.getLogin().contains("@")) {
            isCredentialsExists = credentialsService.checkUserExists(data.getLogin(), true);
            isMail = true;
        } else {
            isCredentialsExists = credentialsService.checkUserExists(data.getLogin(), false);
        }

        if (!isCredentialsExists) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            new ResponseData<>(false, "There is no user with this login!", null)
                    );
        }

        if (!credentialsService.validateCredentials(data.getLogin(), data.getPassword(), isMail)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            new ResponseData<>(false, "User data not matches!", null)
                    );
        }

        return ResponseEntity.ok(new ResponseData<>(true, "User data matches!" , null));
    }

    @PutMapping("/registration")
    public ResponseEntity<ResponseData<Object>> registration(@RequestBody UserRegistrationCredentialsData data) {
        if (credentialsService.checkUserExists(data.getUserName(), false)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            new ResponseData<>(false, "Account with this login already exists!", null)
                    );
        }

        if (credentialsService.checkUserExists(data.getMail(), true)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            new ResponseData<>(false, "Account with this mail already exists!", null)
                    );
        }

        credentialsService.addCredentials(data.getUserName(), data.getMail(), data.getPassword());

        return ResponseEntity.ok(new ResponseData<>(true, "User data matches!" , null));
    }
}
