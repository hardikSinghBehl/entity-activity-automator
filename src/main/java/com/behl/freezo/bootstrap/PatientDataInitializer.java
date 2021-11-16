package com.behl.freezo.bootstrap;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import com.behl.freezo.entity.Patient;
import com.behl.freezo.repository.PatientRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@AllArgsConstructor
@Slf4j
public class PatientDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final PatientRepository patientRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        final var patient = new Patient();
        patient.setFullName("Hardik Singh Behl");
        patient.getActivity().setCreatedBy(4);

        final var savedPatient = patientRepository.save(patient);
        log.info("Patient:{} , saved successfully", savedPatient.getId());

        logPatientDetials();
    }

    private void logPatientDetials() {
        final var patients = patientRepository.findAll();
        patients.forEach(patient -> {
            log.info(patient.toString());
        });
    }

}
