package com.etnetera.hr;

import com.etnetera.hr.data.dto.FrameworkVersionDto;
import com.etnetera.hr.data.dto.HypeLevelBaseDto;
import com.etnetera.hr.data.dto.HypeLevelDto;
import com.etnetera.hr.data.dto.JScriptFrameworkBaseDto;
import com.etnetera.hr.data.dto.JScriptFrameworkDto;
import com.etnetera.hr.data.dto.SearchResultDto;
import com.etnetera.hr.data.dto.filter.BasicExpression;
import com.etnetera.hr.data.dto.filter.FilterDto;
import com.etnetera.hr.data.dto.filter.GroupExpression;
import com.etnetera.hr.data.dto.filter.LogicalOperator;
import com.etnetera.hr.data.dto.filter.OperationType;
import com.etnetera.hr.data.entity.HypeLevel;
import com.etnetera.hr.data.entity.JavaScriptFramework;
import com.etnetera.hr.repository.CrudAndSearchRepository;
import com.etnetera.hr.service.CrudService;
import com.etnetera.hr.service.impl.CrudAndSearchService;
import com.etnetera.hr.utils.FrameworkVersionUtils;
import com.etnetera.hr.utils.HypeLevelTestUtil;
import com.etnetera.hr.utils.JScriptFrameworkUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;


/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JavaScriptFrameworkTests {

    @Autowired
    private CrudAndSearchService<JavaScriptFramework, JScriptFrameworkBaseDto, JScriptFrameworkDto, Long> frameworkService;

    @Autowired
    private CrudService<HypeLevelBaseDto, HypeLevelDto, Long> hypelevelService;

    @Autowired
    private CrudAndSearchRepository<JavaScriptFramework, Long> frameworkRepository;

    @Autowired
    private ObjectMapper objectMapper;


    private final Random random = new Random();

    @Test
	public void createTest() {
        JScriptFrameworkBaseDto frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(
                createAndSaveHypeLevel().getId());
        JScriptFrameworkDto savedFrameworkDto = frameworkService.create(frameworkBaseDto);
        Assert.assertNotNull(savedFrameworkDto);
        Assert.assertTrue(JScriptFrameworkUtils.equals(frameworkBaseDto, savedFrameworkDto));
    }

    @Test
    public void updateAllFieldsTest() {
        JScriptFrameworkBaseDto frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(
                createAndSaveHypeLevel().getId());
        JScriptFrameworkDto savedDto = frameworkService.create(frameworkBaseDto);

        savedDto.setHypeLevelId(createAndSaveHypeLevel().getId());
        savedDto.setVersions(FrameworkVersionUtils.newInstanceVersions());
        savedDto.setDeprecatedDate(LocalDate.now().minusDays(random.nextInt(1000)));
        savedDto.setName(savedDto.getName() + "1");
        JScriptFrameworkDto updatedDto = frameworkService.update(savedDto.getId(), savedDto);
        Assert.assertNotNull(updatedDto);
        Assert.assertTrue(JScriptFrameworkUtils.equals(savedDto, updatedDto));
    }

    @Test
    public void updateOnlyVersions() {
        JScriptFrameworkBaseDto frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(
                createAndSaveHypeLevel().getId());
        JScriptFrameworkDto savedDto = frameworkService.create(frameworkBaseDto);

        List<FrameworkVersionDto> versionDtos = savedDto.getVersions();
        versionDtos.get(0).setVersion(versionDtos.get(0) + "s");
        versionDtos.add(FrameworkVersionUtils.newInstanceVersion());
        JScriptFrameworkDto updatedDto = frameworkService.update(savedDto.getId(), savedDto);
        Assert.assertNotNull(updatedDto);
        Assert.assertTrue(JScriptFrameworkUtils.equals(savedDto, updatedDto));
    }

    @Test
    public void findByIdTest() {
        JScriptFrameworkBaseDto frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(
                createAndSaveHypeLevel().getId());
        JScriptFrameworkDto savedFrameworkDto = frameworkService.create(frameworkBaseDto);

        JScriptFrameworkDto foundFrameworkDto = frameworkService.get(savedFrameworkDto.getId());
        Assert.assertNotNull(foundFrameworkDto);
        Assert.assertTrue(JScriptFrameworkUtils.equals(savedFrameworkDto, foundFrameworkDto));
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteByIdTest() {
        JScriptFrameworkBaseDto frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(createAndSaveHypeLevel().getId());
        JScriptFrameworkDto savedFrameworkDto = frameworkService.create(frameworkBaseDto);
        frameworkService.deleteById(savedFrameworkDto.getId());
        frameworkService.get(savedFrameworkDto.getId());
    }

    @Test
    public void testSearchByEqualAndLike() throws JsonProcessingException {
        List<JScriptFrameworkDto> frameworkDtos = new ArrayList<>();
        JScriptFrameworkBaseDto frameworkBaseDto;
        for(int i = 0; i < 10; i++) {
            frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(createAndSaveHypeLevel().getId());
            frameworkDtos.add(frameworkService.create(frameworkBaseDto));
        }

        int index = random.nextInt(10);
        FilterDto filterDto = createEqualAndLikeFilter((frameworkDtos).get(index));
        Pageable pageable = PageRequest.of(0, 10);
        SearchResultDto<Long, JScriptFrameworkDto> resultDto = frameworkService.findAll(filterDto, pageable);
        Assert.assertNotNull(resultDto);
        Assert.assertEquals(1, resultDto.getTotalElements());
        Assert.assertEquals(frameworkDtos.get(index).getName(), resultDto.getElements().get(0).getName());
    }

    @Test
    public void testSearchByLt() throws JsonProcessingException {
        List<JScriptFrameworkDto> frameworkDtos = new ArrayList<>();
        JScriptFrameworkBaseDto frameworkBaseDto;
        for(int i = 0; i < 10; i++) {
            frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(createAndSaveHypeLevel().getId());
            frameworkDtos.add(frameworkService.create(frameworkBaseDto));
        }

        int index = random.nextInt(10);
        FilterDto filterDto = createLtFilter((frameworkDtos).get(index));
        Pageable pageable = PageRequest.of(0, 10);
        SearchResultDto<Long, JScriptFrameworkDto> resultDto = frameworkService.findAll(filterDto, pageable);
        Assert.assertNotNull(resultDto);
        Assert.assertEquals(1, resultDto.getTotalElements());
        Assert.assertEquals(frameworkDtos.get(index).getName(), resultDto.getElements().get(0).getName());
    }

    @Test
    public void testSearchByHypeLevel() throws JsonProcessingException {
        List<JScriptFrameworkDto> frameworkDtos = new ArrayList<>();
        JScriptFrameworkBaseDto frameworkBaseDto;
        for(int i = 0; i < 10; i++) {
            frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(createAndSaveHypeLevel().getId());
            frameworkDtos.add(frameworkService.create(frameworkBaseDto));
        }

        int index = random.nextInt(10);
        FilterDto filterDto = createHypeLevelEqualFilter(frameworkDtos.get(index));
        Long hypeLevelId = frameworkDtos.get(index).getHypeLevelId();
        Pageable pageable = PageRequest.of(0, 10);
        SearchResultDto<Long, JScriptFrameworkDto> resultDto = frameworkService.findAll(filterDto, pageable);
        Assert.assertNotNull(resultDto);
        Assert.assertEquals( resultDto.getTotalElements(), resultDto.getElements().stream()
                .filter(fw -> fw.getHypeLevelId().equals(hypeLevelId))
                .count());
    }

    @Test
    public void testSearchByFrameworkVersion() throws JsonProcessingException {
        List<JScriptFrameworkDto> frameworkDtos = new ArrayList<>();
        JScriptFrameworkBaseDto frameworkBaseDto;
        for(int i = 0; i < 10; i++) {
            frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(createAndSaveHypeLevel().getId());
            frameworkDtos.add(frameworkService.create(frameworkBaseDto));
        }

        int index = random.nextInt(10);
        FilterDto filterDto = createHypeLevelEqualFilter(frameworkDtos.get(index));
        FrameworkVersionDto frameworkVersionDto = frameworkDtos.get(index).getVersions().get(0);
        Pageable pageable = PageRequest.of(0, 10);
        SearchResultDto<Long, JScriptFrameworkDto> resultDto = frameworkService.findAll(filterDto, pageable);
        Assert.assertNotNull(resultDto);
        Assert.assertEquals( resultDto.getTotalElements(), resultDto.getElements().stream()
                .filter(fw -> FrameworkVersionUtils.equals(fw.getVersions(), frameworkVersionDto)).count());
    }

    private HypeLevelDto createAndSaveHypeLevel() {
        HypeLevelBaseDto baseDto = HypeLevelTestUtil.newInstanceHypeLevel();
        return hypelevelService.create(baseDto);
    }

    @Before
    public void removeAllFrameworks() {
        frameworkRepository.deleteAll();
    }

    private FilterDto createEqualAndLikeFilter(JScriptFrameworkDto dto) throws JsonProcessingException {
        BasicExpression nameExpression = new BasicExpression("name", OperationType.EQUALS, dto.getName());
        BasicExpression nameLikeExpression = new BasicExpression("name", OperationType.LIKE, dto.getName().substring(1, dto.getName().length() - 2));
        BasicExpression deprDateExpression = new BasicExpression("deprecatedDate", OperationType.EQUALS,
                objectMapper.writeValueAsString( dto.getDeprecatedDate() ));
        GroupExpression groupExpression = new GroupExpression(LogicalOperator.AND, Arrays.asList(nameExpression,
                nameLikeExpression, deprDateExpression));
        return new FilterDto(
                LogicalOperator.AND,
                Collections.singletonList(groupExpression));
    }

    private FilterDto createLtFilter(JScriptFrameworkDto dto) throws JsonProcessingException {
        BasicExpression nameExpression = new BasicExpression("name", OperationType.EQUALS, dto.getName());
        LocalDate cmpDate = LocalDate.now().plusYears(100);
        BasicExpression deprDateExpression = new BasicExpression("deprecatedDate", OperationType.LT,
                objectMapper.writeValueAsString( cmpDate ));
        GroupExpression groupExpression = new GroupExpression(LogicalOperator.AND, Arrays.asList(deprDateExpression, nameExpression));
        return new FilterDto(
                LogicalOperator.AND,
                Collections.singletonList(groupExpression));
    }

    private FilterDto createHypeLevelEqualFilter(JScriptFrameworkDto dto) throws JsonProcessingException {
        HypeLevelDto hypeLevelDto = hypelevelService.get(dto.getHypeLevelId());
        BasicExpression hypeLevelScoreExpression = new BasicExpression("hypeLevel.score", OperationType.EQUALS,
                String.valueOf(hypeLevelDto.getScore()));
        GroupExpression groupExpression = new GroupExpression(LogicalOperator.AND, Collections.singletonList(hypeLevelScoreExpression));
        return new FilterDto(
                LogicalOperator.AND,
                Collections.singletonList(groupExpression));
    }

    private FilterDto createVersionEqualFilter(JScriptFrameworkDto dto) throws JsonProcessingException {
        BasicExpression frameworkVersionVersionExpression = new BasicExpression("frameworkVersion.version", OperationType.EQUALS,
                dto.getVersions().get(0).getVersion());
        GroupExpression groupExpression = new GroupExpression(LogicalOperator.AND, Collections.singletonList(frameworkVersionVersionExpression));
        return new FilterDto(
                LogicalOperator.AND,
                Collections.singletonList(groupExpression));
    }

}
