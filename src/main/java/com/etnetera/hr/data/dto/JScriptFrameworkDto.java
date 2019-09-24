package com.etnetera.hr.data.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Full DTO of JavaScriptFramework entity
 */
@Getter
@Setter
public class JScriptFrameworkDto extends JScriptFrameworkBaseDto implements WithId<Long> {
    /** ID of entity */
    private Long id;
}
