package com.behl.freezo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class LoginRequestDto {

    @Schema(example = "octavius@otto.com")
    private final String emailId;
    @Schema(example = "otto@123")
    private final String password;

}
