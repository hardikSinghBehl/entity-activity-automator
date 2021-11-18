package com.behl.freezo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.behl.freezo.dto.AppointmentCreationRequestDto;
import com.behl.freezo.entity.Appointment;
import com.behl.freezo.repository.AppointmentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public void create(final UUID patientId, final AppointmentCreationRequestDto appointmentCreationRequestDto) {
        final var appointment = new Appointment();
        appointment.setPatientId(patientId);
        appointment.setScheduledAt(appointmentCreationRequestDto.getScheduledAt());
        appointmentRepository.save(appointment);
    }

    public void update(final UUID patientId, final Integer appointmentId,
            final AppointmentCreationRequestDto appointmentUpdationDto) {
        final var appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        appointment.setScheduledAt(appointmentUpdationDto.getScheduledAt());
        appointmentRepository.save(appointment);
    }

    public List<Appointment> retreive(final UUID patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

}
