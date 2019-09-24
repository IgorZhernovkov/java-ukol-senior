package com.etnetera.hr.controller;

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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class JSciptFrameworkControllerTest {
    @Autowired
    private CrudAndSearchService<JavaScriptFramework, JScriptFrameworkBaseDto, JScriptFrameworkDto, Long> frameworkService;

    @Autowired
    private CrudService<HypeLevelBaseDto, HypeLevelDto, Long> hypelevelService;

    @Autowired
    private CrudAndSearchRepository<JavaScriptFramework, Long> frameworkRepository;

    @Autowired
    private CrudRepository<HypeLevel, Long> hypeLevelRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private final Random random = new Random();

    private static String BASE_URL = "http://localhost:8080/framework";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context).build();
        frameworkRepository.deleteAll();
        hypeLevelRepository.deleteAll();
    }

    @Test
    @Transactional
    public void createTest() {
        JScriptFrameworkBaseDto frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(
                createAndSaveHypeLevel().getId());
        JScriptFrameworkDto savedFrameworkDto = frameworkService.create(frameworkBaseDto);
        Assert.assertNotNull(savedFrameworkDto);
        Assert.assertTrue(JScriptFrameworkUtils.equals(frameworkBaseDto, savedFrameworkDto));
    }

    @Test
    @Transactional
    public void updateAllFieldsTest() throws Exception {
        JScriptFrameworkBaseDto newDto = JScriptFrameworkUtils.newInstanceJScriptFramework(
                createAndSaveHypeLevel().getId());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDto)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        String jsonResult = mvcResult.getResponse().getContentAsString();
        JScriptFrameworkDto savedDto = objectMapper.readValue(jsonResult, JScriptFrameworkDto.class);

        savedDto.setHypeLevelId(createAndSaveHypeLevel().getId());
        savedDto.setVersions(FrameworkVersionUtils.newInstanceVersions());
        savedDto.setDeprecatedDate(LocalDate.now().minusDays(random.nextInt(1000)));
        savedDto.setName(savedDto.getName() + "1");

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + savedDto.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(JScriptFrameworkUtils.convert(savedDto))))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        jsonResult = mvcResult.getResponse().getContentAsString();
        JScriptFrameworkDto updatedDto = objectMapper.readValue(jsonResult, JScriptFrameworkDto.class);

        Assert.assertNotNull(updatedDto);
        Assert.assertTrue(JScriptFrameworkUtils.equals(savedDto, updatedDto));
    }

    @Test
    @Transactional
    public void findByIdTest() throws Exception {
        JScriptFrameworkBaseDto frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(
                createAndSaveHypeLevel().getId());
        JScriptFrameworkDto savedDto = frameworkService.create(frameworkBaseDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + savedDto.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();
        JScriptFrameworkDto foundDto = objectMapper.readValue(jsonResult, JScriptFrameworkDto.class);

        Assert.assertNotNull(foundDto);
        Assert.assertTrue(JScriptFrameworkUtils.equals(savedDto, foundDto));
    }

    @Test(expected = NoSuchElementException.class)
    @Transactional
    public void deleteByIdTest() throws Exception {
        JScriptFrameworkBaseDto frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(createAndSaveHypeLevel().getId());
        JScriptFrameworkDto savedDto = frameworkService.create(frameworkBaseDto);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + savedDto.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        frameworkService.get(savedDto.getId());
    }

    @Test
    @Transactional
    public void testSearchByEqualAndLike() throws Exception {
        List<JScriptFrameworkDto> frameworkDtos = new ArrayList<>();
        JScriptFrameworkBaseDto frameworkBaseDto;
        for(int i = 0; i < 10; i++) {
            frameworkBaseDto = JScriptFrameworkUtils.newInstanceJScriptFramework(createAndSaveHypeLevel().getId());
            frameworkDtos.add(frameworkService.create(frameworkBaseDto));
        }

        int index = random.nextInt(10);
        FilterDto filterDto = createEqualAndLikeFilter((frameworkDtos).get(index));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/search")
                .requestAttr("page", 0)
                .requestAttr("size", 10)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filterDto)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        String jsonResult = mvcResult.getResponse().getContentAsString();
        SearchResultDto<Long, JScriptFrameworkDto> resultDto = objectMapper.readValue(jsonResult,
                new TypeReference<SearchResultDto<Long, JScriptFrameworkDto>>(){});

        Assert.assertNotNull(resultDto);
        Assert.assertEquals(1, resultDto.getTotalElements());
        Assert.assertEquals(frameworkDtos.get(index).getName(), resultDto.getElements().get(0).getName());
    }

    private HypeLevelDto createAndSaveHypeLevel() {
        HypeLevelBaseDto baseDto = HypeLevelTestUtil.newInstanceHypeLevel();
        return hypelevelService.create(baseDto);
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

}
