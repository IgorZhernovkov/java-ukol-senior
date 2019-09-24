package com.etnetera.hr.data.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Full DTO of Framework version entity
 */
@NoArgsConstructor
@Getter
@Setter
public class FrameworkVersionDto extends FrameworkVersionBaseDto implements WithId<Long> {
    /**  ID of entity  */
    private Long id;

    public FrameworkVersionDto(String version, LocalDate releaseDate) {
        super(version, releaseDate);
    }
}
