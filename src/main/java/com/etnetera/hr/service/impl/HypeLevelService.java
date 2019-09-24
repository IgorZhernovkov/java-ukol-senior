package com.etnetera.hr.service.impl;

import com.etnetera.hr.data.converter.Converter;
import com.etnetera.hr.data.dto.HypeLevelBaseDto;
import com.etnetera.hr.data.dto.HypeLevelDto;
import com.etnetera.hr.data.entity.HypeLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;

@Service
@Transactional
@Validated
@Slf4j
public class HypeLevelService extends  BasicCrudService<HypeLevel, HypeLevelBaseDto, HypeLevelDto, Long> {

    private final Dictionary<HypeLevelBaseDto, HypeLevelDto, HypeLevel> dictionary;

    public HypeLevelService(Converter<HypeLevelBaseDto, HypeLevelDto, HypeLevel> converter, CrudRepository<HypeLevel, Long> repository) {
        super(converter, repository);
        dictionary = new HypeLevelDictionary();
    }

    @Override
    protected Dictionary<HypeLevelBaseDto, HypeLevelDto, HypeLevel> getDictionary() {
        return dictionary;
    }

    public class HypeLevelDictionary implements Dictionary<HypeLevelBaseDto, HypeLevelDto, HypeLevel> {

        @Override
        public HypeLevel newEntity() {
            return new HypeLevel();
        }

        @Override
        public String getEntityClassName() {
            return HypeLevel.class.getSimpleName();
        }

        @Override
        public HypeLevelBaseDto newBaseDto() {
            return new HypeLevelBaseDto();
        }

        @Override
        public HypeLevelDto newDto() {
            return new HypeLevelDto();
        }
    }
}
