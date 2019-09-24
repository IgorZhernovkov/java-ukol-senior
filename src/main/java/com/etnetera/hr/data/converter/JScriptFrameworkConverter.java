package com.etnetera.hr.data.converter;

import com.etnetera.hr.data.dto.FrameworkVersionBaseDto;
import com.etnetera.hr.data.dto.FrameworkVersionDto;
import com.etnetera.hr.data.dto.HypeLevelBaseDto;
import com.etnetera.hr.data.dto.HypeLevelDto;
import com.etnetera.hr.data.dto.JScriptFrameworkBaseDto;
import com.etnetera.hr.data.dto.JScriptFrameworkDto;
import com.etnetera.hr.data.entity.FrameworkVersion;
import com.etnetera.hr.data.entity.HypeLevel;
import com.etnetera.hr.data.entity.JavaScriptFramework;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of converter between entity and it's DTOs for the JavaScriptFramework entity
 */
@Component
@RequiredArgsConstructor
public class JScriptFrameworkConverter implements Converter<JScriptFrameworkBaseDto, JScriptFrameworkDto, JavaScriptFramework> {

    private final Converter<FrameworkVersionBaseDto, FrameworkVersionDto, FrameworkVersion> frameworkVersionConverter;
    private final CrudRepository<HypeLevel, Long> hypeLevelRepository;

    @Override
    public JScriptFrameworkBaseDto convertToBaseDto(JavaScriptFramework javaScriptFramework, JScriptFrameworkBaseDto jScriptFrameworkBaseDto) {
        jScriptFrameworkBaseDto.setName(javaScriptFramework.getName());
        jScriptFrameworkBaseDto.setDeprecatedDate(javaScriptFramework.getDeprecatedDate());
        if(javaScriptFramework.getHypeLevel() != null) {
            jScriptFrameworkBaseDto.setHypeLevelId(javaScriptFramework.getHypeLevel() != null
                    ? javaScriptFramework.getHypeLevel().getId() : null);
        }
        if(javaScriptFramework.getVersions() != null && !javaScriptFramework.getVersions().isEmpty()) {
            jScriptFrameworkBaseDto.setVersions(javaScriptFramework.getVersions().stream().map(
                    version -> frameworkVersionConverter.convertToDto(version, new FrameworkVersionDto())).collect(Collectors.toList())
            );
        }
        return jScriptFrameworkBaseDto;
    }

    @Override
    public JScriptFrameworkDto convertToDto(JavaScriptFramework javaScriptFramework, JScriptFrameworkDto jScriptFrameworkDto) {
        convertToBaseDto(javaScriptFramework, jScriptFrameworkDto);
        jScriptFrameworkDto.setId(javaScriptFramework.getId());
        return  jScriptFrameworkDto;
    }

    @Override
    public JavaScriptFramework convertToEntity(JScriptFrameworkBaseDto jScriptFrameworkBaseDto, JavaScriptFramework javaScriptFramework) {
        javaScriptFramework.setName(jScriptFrameworkBaseDto.getName());
        javaScriptFramework.setDeprecatedDate(jScriptFrameworkBaseDto.getDeprecatedDate());
        if(jScriptFrameworkBaseDto.getHypeLevelId() != null) {
            Optional<HypeLevel> hypeLevel = hypeLevelRepository.findById(jScriptFrameworkBaseDto.getHypeLevelId());
            if (!hypeLevel.isPresent()) {
                throw new NoSuchElementException(String.format("HypeLevel with id='%s' doesn't exist", jScriptFrameworkBaseDto.getHypeLevelId()));
            }
            javaScriptFramework.setHypeLevel(hypeLevel.get());
        }
        if(jScriptFrameworkBaseDto.getVersions() != null && !jScriptFrameworkBaseDto.getVersions().isEmpty()) {
            javaScriptFramework.setVersions(jScriptFrameworkBaseDto.getVersions().stream().map(
                    versionDto -> frameworkVersionConverter.convertToEntity(versionDto, new FrameworkVersion())).collect(Collectors.toList())
            );
        }
        return javaScriptFramework;
    }

    @Override
    public JavaScriptFramework convertToEntity(JScriptFrameworkDto jScriptFrameworkDto, JavaScriptFramework javaScriptFramework) {
        convertToEntity((JScriptFrameworkBaseDto) jScriptFrameworkDto, javaScriptFramework);
        javaScriptFramework.setId(jScriptFrameworkDto.getId());
        return javaScriptFramework;
    }
}
