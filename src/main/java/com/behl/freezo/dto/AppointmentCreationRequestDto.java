package com.behl.freezo.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class AppointmentCreationRequestDto {

    private final LocalDateTime scheduledAt;

}
