package com.behl.freezo.configuration.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.behl.freezo.configuration.security.LoggedInDoctorDetailProvider;
import com.behl.freezo.configuration.security.utility.JwtUtils;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LoggedInDoctorDetailStorageFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            final var authorizationHeader = request.getHeader("Authorization");
            final var token = authorizationHeader.substring(7);
            final var userId = jwtUtils.extractDoctorId(token);

            LoggedInDoctorDetailProvider.setUserId(userId);
        }
        filterChain.doFilter(request, response);
    }

}
