package com.behl.freezo.security.utility;

import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.behl.freezo.entity.Doctor;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityUtils {

    public User convert(Doctor doctor) {
        return new User(doctor.getEmailId(), doctor.getPassword(), List.of());
    }

}