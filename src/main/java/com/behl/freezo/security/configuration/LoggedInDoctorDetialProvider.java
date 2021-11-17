package com.behl.freezo.security.configuration;

import java.util.UUID;

public class LoggedInDoctorDetialProvider {

    private static final ThreadLocal<UUID> userId = new ThreadLocal<UUID>();

    public static void setUserId(final UUID id) {
        userId.set(id);
    }

    public static UUID getId() {
        return userId.get();
    }

}
