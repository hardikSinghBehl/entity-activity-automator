package com.behl.freezo.configuration.security;

import java.util.UUID;

public class LoggedInDoctorDetailProvider {

    private static final ThreadLocal<UUID> userId = new ThreadLocal<UUID>();

    public static void setUserId(final UUID id) {
        userId.set(id);
    }

    public static UUID getId() {
        return userId.get();
    }

}
