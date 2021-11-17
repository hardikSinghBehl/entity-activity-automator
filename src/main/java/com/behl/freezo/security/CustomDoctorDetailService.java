package com.behl.freezo.security;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.behl.freezo.repository.DoctorRepository;
import com.behl.freezo.security.utility.SecurityUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomDoctorDetailService implements UserDetailsService {

    private final DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return SecurityUtils.convert(doctorRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UsernameNotFoundException("Bad Credentials")));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}