package com.etnetera.hr.service.impl;

import com.etnetera.hr.data.converter.Converter;
import com.etnetera.hr.data.dto.JScriptFrameworkBaseDto;
import com.etnetera.hr.data.dto.JScriptFrameworkDto;
import com.etnetera.hr.data.dto.SearchResultDto;
import com.etnetera.hr.data.entity.JavaScriptFramework;
import com.etnetera.hr.repository.CrudAndSearchRepository;
import com.etnetera.hr.specification.factory.SpecificationFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class JScriptFrameworkService extends CrudAndSearchService<JavaScriptFramework, JScriptFrameworkBaseDto, JScriptFrameworkDto, Long> {

    private final Dictionary<JScriptFrameworkBaseDto, JScriptFrameworkDto, JavaScriptFramework> dictionary;

    public JScriptFrameworkService(Converter<JScriptFrameworkBaseDto, JScriptFrameworkDto, JavaScriptFramework> converter,
                                   CrudAndSearchRepository<JavaScriptFramework, Long> repository,
                                   SpecificationFactory<JavaScriptFramework> specificationFactory) {
        super(converter, repository, specificationFactory);
        dictionary = new JScriptFrameworkDictionary();
    }

    @Override
    protected Dictionary<JScriptFrameworkBaseDto, JScriptFrameworkDto, JavaScriptFramework> getDictionary() {
        return dictionary;
    }

    @Override
    protected JScriptFrameworkDto newInstanceDto() {
        return new JScriptFrameworkDto();
    }

    @Override
    SearchResultDto<Long, JScriptFrameworkDto> convertToSearchResult(Page<JScriptFrameworkDto> pageofDtos) {
        return new SearchResultDto<>(pageofDtos.getTotalPages(), pageofDtos.getTotalElements(),
                pageofDtos.getContent());
    }

    public class JScriptFrameworkDictionary implements Dictionary<JScriptFrameworkBaseDto, JScriptFrameworkDto, JavaScriptFramework> {

        @Override
        public JavaScriptFramework newEntity() {
            return new JavaScriptFramework();
        }

        @Override
        public String getEntityClassName() {
            return JavaScriptFramework.class.getSimpleName();
        }

        @Override
        public JScriptFrameworkBaseDto newBaseDto() {
            return new JScriptFrameworkBaseDto();
        }

        @Override
        public JScriptFrameworkDto newDto() {
            return new JScriptFrameworkDto();
        }
    }
}
