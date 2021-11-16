package com.behl.freezo.entity.embeddable;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Activity {

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", nullable = false)
    private Integer updatedBy;

}
