package com.behl.freezo.service;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.behl.freezo.configuration.security.utility.JwtUtils;
import com.behl.freezo.dto.LoginRequestDto;
import com.behl.freezo.repository.DoctorRepository;
import com.behl.freezo.utility.ResponseProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ResponseProvider responseProvider;

    public Map<String, String> login(final LoginRequestDto loginRequestDto) {
        final var doctor = doctorRepository.findByEmailId(loginRequestDto.getEmailId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), doctor.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return responseProvider.authenticated(jwtUtils.generateToken(doctor));
    }

}
