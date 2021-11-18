package com.behl.freezo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.behl.freezo.entity.embeddable.Activity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "appointments")
@Data
public class Appointment implements Serializable {

    private static final long serialVersionUID = -2095053968271692239L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Integer id;

    @Column(name = "patient_id", nullable = false)
    private UUID patientId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false, updatable = false, insertable = false)
    private Patient patient;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Embedded
    @Setter(AccessLevel.NONE)
    private Activity activity = new Activity();

}
