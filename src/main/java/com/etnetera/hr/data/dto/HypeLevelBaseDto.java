package com.etnetera.hr.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Base DTO of HypeLevel entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HypeLevelBaseDto {
    /** Score of hype level */
    @NotNull
    private Integer score;

    /** Name of hype level */
    @NotBlank
    private String name;
}
