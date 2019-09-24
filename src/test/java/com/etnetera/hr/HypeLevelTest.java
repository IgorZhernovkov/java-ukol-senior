package com.etnetera.hr;

import com.etnetera.hr.data.dto.HypeLevelBaseDto;
import com.etnetera.hr.data.dto.HypeLevelDto;
import com.etnetera.hr.data.entity.HypeLevel;
import com.etnetera.hr.service.CrudService;
import com.etnetera.hr.utils.HypeLevelTestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HypeLevelTest {

    @Autowired
    private CrudService<HypeLevelBaseDto, HypeLevelDto, Long> service;

    @Autowired
    private CrudRepository<HypeLevel, Long> hypeLevelRepository;

    @Test
    public void createTest() {
        HypeLevelBaseDto newDto = HypeLevelTestUtil.newInstanceHypeLevel();
        HypeLevelDto savedDto = service.create(newDto);
        Assert.assertNotNull(savedDto);
        Assert.assertTrue(HypeLevelTestUtil.equals(newDto, savedDto));
    }

    @Test
    public void updateTest() {
        Random random = new Random();
        HypeLevelBaseDto newDto = HypeLevelTestUtil.newInstanceHypeLevel();
        HypeLevelDto savedDto = service.create(newDto);
        savedDto.setScore(random.nextInt(100));
        savedDto.setName("name_" + random.nextLong());
        HypeLevelDto updatedDto = service.update(savedDto.getId(), savedDto);
        Assert.assertNotNull(updatedDto);
        Assert.assertTrue(HypeLevelTestUtil.equals(savedDto, updatedDto));
    }

    @Test
    public void findByIdTest() {
        HypeLevelBaseDto newDto = HypeLevelTestUtil.newInstanceHypeLevel();
        HypeLevelDto savedDto = service.create(newDto);
        HypeLevelDto foundDto = service.get(savedDto.getId());
        Assert.assertNotNull(foundDto);
        Assert.assertTrue(HypeLevelTestUtil.equals(savedDto, foundDto));
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteByIdTest() {
        HypeLevelBaseDto newDto = HypeLevelTestUtil.newInstanceHypeLevel();
        HypeLevelDto savedDto = service.create(newDto);
        service.deleteById(savedDto.getId());
        service.get(savedDto.getId());
    }

    @Before
    public void deleteAllHypeLevels() {
        hypeLevelRepository.deleteAll();
    }
}
