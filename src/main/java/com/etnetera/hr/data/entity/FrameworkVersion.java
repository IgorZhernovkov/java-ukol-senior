package com.etnetera.hr.data.entity;

import com.etnetera.hr.data.dto.WithId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Entity for JavaScriptFramework entity
 */
@Entity
@Getter
@Setter
public class FrameworkVersion implements WithId<Long> {
    /** ID of entity */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** Name of version */
    @Column(nullable = false, length = 100)
    private String version;

    /** Release date of version */
    @Column(nullable = false)
    private LocalDate releaseDate;
}
