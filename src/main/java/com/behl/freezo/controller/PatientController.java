package com.behl.freezo.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.behl.freezo.dto.PatientCreationRequestDto;
import com.behl.freezo.dto.PatientDto;
import com.behl.freezo.service.PatientService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping(value = "/patient", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Map<String, String>> patientCreationhandler(
            @RequestBody(required = true) final PatientCreationRequestDto patientCreationRequestDto) {
        return ResponseEntity.ok(patientService.create(patientCreationRequestDto));
    }

    @PutMapping(value = "/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> patientUpdationhandler(
            @PathVariable(required = true, name = "patientId") final UUID patientId,
            @RequestBody(required = true) final PatientCreationRequestDto patientUpdationRequestDto) {
        patientService.update(patientId, patientUpdationRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<PatientDto> patientRetreivalhandler(
            @PathVariable(required = true, name = "patientId") final UUID patientId) {
        return ResponseEntity.ok(patientService.get(patientId));
    }

    @GetMapping(value = "/patient", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<PatientDto>> patientListRetreivalhandler() {
        return ResponseEntity.ok(patientService.get());
    }

}
