package com.behl.freezo.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.behl.freezo.dto.AppointmentDto;
import com.behl.freezo.dto.PatientDto;
import com.behl.freezo.entity.Appointment;
import com.behl.freezo.entity.Patient;
import com.behl.freezo.entity.embeddable.Activity;

@Component
public class ResponseProvider {

    public Map<String, String> savedPatientResponse(final Patient savedPatient) {
        final var response = new HashMap<String, String>();
        response.put("patient-id", savedPatient.getId().toString());
        return response;
    }

    public PatientDto patientResponse(final Patient patient, final Activity patientActivity) {
        return PatientDto.builder().id(patient.getId()).fullName(patient.getFullName())
                .createdAt(patientActivity.getCreatedAt()).createdBy(patientActivity.getCreatedBy())
                .updatedAt(patientActivity.getUpdatedAt()).updatedBy(patientActivity.getUpdatedBy())
                .isActive(patientActivity.isActive()).build();
    }

    public AppointmentDto appointmentResponse(final Appointment appointment, final Activity appointmentActivity) {
        return AppointmentDto.builder().id(appointment.getId()).patientId(appointment.getPatientId())
                .scheduledAt(appointment.getScheduledAt()).createdAt(appointmentActivity.getCreatedAt())
                .createdBy(appointmentActivity.getCreatedBy()).updatedAt(appointmentActivity.getUpdatedAt())
                .updatedBy(appointmentActivity.getUpdatedBy()).isActive(appointmentActivity.isActive()).build();
    }

    public Map<String, String> authenticated(final String jwt) {
        final var response = new HashMap<String, String>();
        response.put("JWT", jwt);
        return response;
    }

}
