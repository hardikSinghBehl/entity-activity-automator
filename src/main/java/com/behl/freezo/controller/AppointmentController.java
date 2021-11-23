package com.behl.freezo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.behl.freezo.dto.AppointmentCreationRequestDto;
import com.behl.freezo.dto.AppointmentDto;
import com.behl.freezo.service.AppointmentService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping(value = "/patient/{patientId}/appointment")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> appointmentCreationRequestHandler(
            @PathVariable(required = true, name = "patientId") final UUID patientId,
            @RequestBody(required = true) final AppointmentCreationRequestDto appointmentCreationRequestDto) {
        appointmentService.create(patientId, appointmentCreationRequestDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/patient/{patientId}/appointment/{appointmentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> appointmentUpdationRequestHandler(
            @PathVariable(required = true, name = "patientId") final UUID patientId,
            @PathVariable(required = true, name = "appointmentId") final Integer appointmentId,
            @RequestBody(required = true) final AppointmentCreationRequestDto appointmentUpdationDto) {
        appointmentService.update(patientId, appointmentId, appointmentUpdationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/patient/{patientId}/appointment")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<AppointmentDto>> appointmentsRetreivalHandler(
            @PathVariable(required = true, name = "patientId") final UUID patientId) {
        return ResponseEntity.ok(appointmentService.retreive(patientId));
    }

    @DeleteMapping(value = "/patient/{patientId}/appointment/{appointmentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> appointmentDeletionRequestHandler(
            @PathVariable(required = true, name = "patientId") final UUID patientId,
            @PathVariable(required = true, name = "appointmentId") final Integer appointmentId) {
        appointmentService.delete(patientId, appointmentId);
        return ResponseEntity.ok().build();
    }

}
