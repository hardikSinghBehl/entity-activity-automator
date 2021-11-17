package com.behl.freezo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class PatientCreationRequestDto {

    private final String fullName;

}
