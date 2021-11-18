package com.behl.freezo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.behl.freezo.dto.PatientCreationRequestDto;
import com.behl.freezo.entity.Patient;
import com.behl.freezo.repository.PatientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public Map<String, String> create(final PatientCreationRequestDto patientCreationRequestDto) {
        final var patient = new Patient();
        patient.setFullName(patientCreationRequestDto.getFullName());

        final var savedPatient = patientRepository.save(patient);

        final var response = new HashMap<String, String>();
        response.put("patient-id", savedPatient.getId().toString());
        return response;
    }

    public Patient get(final UUID patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public void update(final UUID patientId, final PatientCreationRequestDto patientUpdationRequestDto) {
        final var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        patient.setFullName(patientUpdationRequestDto.getFullName());

        patientRepository.save(patient);
    }

}
