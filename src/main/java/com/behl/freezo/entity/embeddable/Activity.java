package com.behl.freezo.entity.embeddable;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Embeddable
@Data
public class Activity {

    private LocalDateTime createdAt;
    private Integer createdBy;
    private LocalDateTime updatedAt;
    private Integer updatedBy;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now(ZoneId.of("+00:00"));
        this.updatedAt = LocalDateTime.now(ZoneId.of("+00:00"));

        if (this.createdBy == null) {
            this.createdBy = 1;
            this.updatedBy = 1;
        }
        if (this.createdBy != null && this.updatedBy == null)
            this.updatedBy = this.createdBy;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now(ZoneId.of("+00:00"));
    }

}
