package com.etnetera.hr.controller;

import com.etnetera.hr.data.dto.HypeLevelBaseDto;
import com.etnetera.hr.data.dto.HypeLevelDto;
import com.etnetera.hr.data.entity.HypeLevel;
import com.etnetera.hr.service.CrudService;
import com.etnetera.hr.utils.HypeLevelTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.NoSuchElementException;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HypeLevelControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CrudRepository<HypeLevel, Long> hypeLevelRepository;

    private MockMvc mockMvc;

    private static String BASE_URL = "http://localhost:8080/hypelevel";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context).build();
        hypeLevelRepository.deleteAll();
    }


    @Autowired
    private CrudService<HypeLevelBaseDto, HypeLevelDto, Long> service;

    @Test
    public void createTest() throws Exception {
        HypeLevelBaseDto newDto = HypeLevelTestUtil.newInstanceHypeLevel();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newDto)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        String jsonResult = mvcResult.getResponse().getContentAsString();
        HypeLevelDto savedDto = objectMapper.readValue(jsonResult, HypeLevelDto.class);

        Assert.assertNotNull(newDto);
        Assert.assertTrue(HypeLevelTestUtil.equals(newDto, savedDto));
    }

    @Test
    public void updateTest() throws Exception {
        Random random = new Random();
        HypeLevelBaseDto newDto = HypeLevelTestUtil.newInstanceHypeLevel();
        HypeLevelDto savedDto = service.create(newDto);
        savedDto.setScore(random.nextInt(100));
        savedDto.setName("name_" + random.nextLong());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + savedDto.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(HypeLevelTestUtil.convert(savedDto))))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();
        HypeLevelDto updatedDto = objectMapper.readValue(jsonResult, HypeLevelDto.class);

        Assert.assertNotNull(updatedDto);
        Assert.assertTrue(HypeLevelTestUtil.equals(savedDto, updatedDto));
    }

    @Test
    public void findByIdTest() throws Exception {
        HypeLevelBaseDto newDto = HypeLevelTestUtil.newInstanceHypeLevel();
        HypeLevelDto savedDto = service.create(newDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + savedDto.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedDto)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        String jsonResult = mvcResult.getResponse().getContentAsString();
        HypeLevelDto foundDto = objectMapper.readValue(jsonResult, HypeLevelDto.class);

        Assert.assertNotNull(foundDto);
        Assert.assertTrue(HypeLevelTestUtil.equals(savedDto, foundDto));
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteByIdTest() throws Exception {
        HypeLevelBaseDto newDto = HypeLevelTestUtil.newInstanceHypeLevel();
        HypeLevelDto savedDto = service.create(newDto);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + savedDto.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        service.get(savedDto.getId());
    }
}
