package com.behl.freezo.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.behl.freezo.dto.PatientCreationRequestDto;
import com.behl.freezo.dto.PatientDto;
import com.behl.freezo.entity.Patient;
import com.behl.freezo.repository.PatientRepository;
import com.behl.freezo.utility.ResponseProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final ResponseProvider responseProvider;

    public Map<String, String> create(final PatientCreationRequestDto patientCreationRequestDto) {
        final var patient = new Patient();
        patient.setFullName(patientCreationRequestDto.getFullName());

        final var savedPatient = patientRepository.save(patient);

        return responseProvider.savedPatientResponse(savedPatient);
    }

    public PatientDto get(final UUID patientId) {
        final var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        final var patientActivity = patient.getActivity();

        return responseProvider.patientResponse(patient, patientActivity);
    }

    public void update(final UUID patientId, final PatientCreationRequestDto patientUpdationRequestDto) {
        final var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        patient.setFullName(patientUpdationRequestDto.getFullName());

        patientRepository.save(patient);
    }

    public List<PatientDto> get() {
        return patientRepository.findAll().parallelStream().map(patient -> {
            final var patientActivity = patient.getActivity();
            return responseProvider.patientResponse(patient, patientActivity);
        }).collect(Collectors.toList());
    }

    public void delete(final UUID patientId) {
        final var patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        patient.getActivity().setActive(false);

        patientRepository.save(patient);
    }

}
