package com.etnetera.hr.controller;

import com.etnetera.hr.data.dto.HypeLevelBaseDto;
import com.etnetera.hr.data.dto.HypeLevelDto;
import com.etnetera.hr.service.CrudService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for all basic CRUD operations on HypeLevel
 */
@RestController
@Validated
@RequestMapping(value = "hypelevel")
public class HypeLevelController extends BasicCrudController<HypeLevelBaseDto, HypeLevelDto, Long> {

    public HypeLevelController(CrudService<HypeLevelBaseDto, HypeLevelDto, Long> service) {
        super(service, HypeLevelDto.class);
    }
}
