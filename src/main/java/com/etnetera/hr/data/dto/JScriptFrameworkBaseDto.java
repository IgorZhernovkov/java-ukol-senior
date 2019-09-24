package com.etnetera.hr.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Base DTO of JavaScriptFramework entity
 */
@Getter
@Setter
@AllArgsConstructor
public class JScriptFrameworkBaseDto {
    /** Name of framework */
    @NotNull
    @NotBlank
    private String name;
    /** List of frameworks's version */;
    private List<FrameworkVersionDto> versions;
    /** Hype level's ID of framework */
    @NotNull
    private Long hypeLevelId;
    /** Deprecared date of framework */
    private LocalDate deprecatedDate;

    public JScriptFrameworkBaseDto() {
        this.versions = new ArrayList<>();
    }
}
