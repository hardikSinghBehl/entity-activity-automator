package com.behl.freezo.entity.embeddable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.behl.freezo.configuration.security.LoggedInDoctorDetailProvider;

import lombok.Data;

@Embeddable
@Data
public class Activity implements Serializable {

    private static final long serialVersionUID = -2198531438505511438L;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @Column(name = "updated_by", nullable = false)
    private UUID updatedBy;
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @PrePersist
    void onCreate() {
        this.isActive = true;

        this.createdAt = LocalDateTime.now(ZoneId.of("+00:00"));
        this.updatedAt = LocalDateTime.now(ZoneId.of("+00:00"));

        this.createdBy = LoggedInDoctorDetailProvider.getId();
        this.updatedBy = LoggedInDoctorDetailProvider.getId();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now(ZoneId.of("+00:00"));
        this.updatedBy = LoggedInDoctorDetailProvider.getId();
    }

}
