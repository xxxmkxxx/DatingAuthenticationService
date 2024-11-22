package com.dating.authentication.controller;

import com.dating.authentication.data.ResponseData;
import com.dating.authentication.data.UserRegistrationCredentialsData;
import com.dating.authentication.data.UserSingInCredentialsData;
import com.dating.authentication.service.CredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0.0.1/authentication")
@AllArgsConstructor
public class AuthenticationController {
    private CredentialsService credentialsService;

    @PostMapping("/login")
    public ResponseEntity<ResponseData<Void>> login(@RequestBody UserSingInCredentialsData data) {
        return ResponseEntity.ok(credentialsService.validateCredentials(data));
    }

    @PutMapping("/registration")
    public ResponseEntity<ResponseData<Void>> registration(@RequestBody UserRegistrationCredentialsData data) {
        return ResponseEntity.ok(credentialsService.addCredentials(data));
    }
}
