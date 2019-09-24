package com.etnetera.hr.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

/**
 * Base DTO of FrameworkVersion entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FrameworkVersionBaseDto {
    /** Name of version */
    @NotNull
    @NotBlank
    private String version;

    /** Release date of framework */
    @NotNull
    @PastOrPresent
    private LocalDate releaseDate;
}
