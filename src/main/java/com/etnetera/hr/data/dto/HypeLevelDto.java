package com.etnetera.hr.data.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Full DTO of HypeLevel entity
 */
@Getter
@Setter
public class HypeLevelDto extends HypeLevelBaseDto implements WithId<Long> {
    /** ID of entity */
    private Long id;

    public HypeLevelDto(Integer score, String name, Long id) {
        super(score, name);
        this.id = id;
    }

    public HypeLevelDto() {
        super(0, "");
    }
}
