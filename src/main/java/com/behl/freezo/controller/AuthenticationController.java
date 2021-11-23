package com.behl.freezo.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.behl.freezo.dto.LoginRequestDto;
import com.behl.freezo.service.DoctorService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final DoctorService doctorService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Returns JWT representing logged in user, to be used to access private APIs")
    public ResponseEntity<Map<String, String>> loginRequestHandler(
            @RequestBody(required = true) final LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(doctorService.login(loginRequestDto));
    }

}
