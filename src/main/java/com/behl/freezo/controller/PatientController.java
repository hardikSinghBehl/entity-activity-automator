package com.behl.freezo.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping(value = "/patient", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Creates a patient record")
    public ResponseEntity<Map<String, String>> patientCreationHandler(
            @RequestBody(required = true) final PatientCreationRequestDto patientCreationRequestDto) {
        return ResponseEntity.ok(patientService.create(patientCreationRequestDto));
    }

    @PutMapping(value = "/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Updates patient record details")
    public ResponseEntity<?> patientUpdationHandler(
            @PathVariable(required = true, name = "patientId") final UUID patientId,
            @RequestBody(required = true) final PatientCreationRequestDto patientUpdationRequestDto) {
        patientService.update(patientId, patientUpdationRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Returns patient record details")
    public ResponseEntity<PatientDto> patientRetreivalHandler(
            @PathVariable(required = true, name = "patientId") final UUID patientId) {
        return ResponseEntity.ok(patientService.get(patientId));
    }

    @GetMapping(value = "/patient", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Returns all patient records in system")
    public ResponseEntity<List<PatientDto>> patientListRetreivalHandler() {
        return ResponseEntity.ok(patientService.get());
    }

    @DeleteMapping(value = "/patient/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Deletesa a patient record")
    public ResponseEntity<?> patientDeletionHandler(
            @PathVariable(required = true, name = "patientId") final UUID patientId) {
        patientService.delete(patientId);
        return ResponseEntity.ok().build();
    }

}
