package com.etnetera.hr.controller;

import com.etnetera.hr.data.dto.JScriptFrameworkBaseDto;
import com.etnetera.hr.data.dto.JScriptFrameworkDto;
import com.etnetera.hr.data.entity.JavaScriptFramework;
import com.etnetera.hr.service.impl.CrudAndSearchService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *	REST controller for basic CRUD operations and advanced searching operations on the JavascriptFramework entity
 */
@RestController
@RequestMapping("framework")
@Validated
public class JavaScriptFrameworkController  extends CrudAndSearchController<Long, JavaScriptFramework, JScriptFrameworkBaseDto, JScriptFrameworkDto>{

	public JavaScriptFrameworkController(CrudAndSearchService<JavaScriptFramework, JScriptFrameworkBaseDto,
            JScriptFrameworkDto, Long> service) {
	    super(service, JScriptFrameworkDto.class);
    }
}
