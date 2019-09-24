package com.etnetera.hr;

import com.etnetera.hr.data.dto.filter.BasicExpression;
import com.etnetera.hr.data.dto.filter.OperationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicExpressionTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void serializeTest() throws IOException {
        BasicExpression basicExpression = new BasicExpression("field", OperationType.EQUALS, objectMapper.writeValueAsString(LocalDate.now()));
        String json = objectMapper.writeValueAsString(basicExpression);
        Assert.assertNotNull(json);

        BasicExpression basicExpression1 = objectMapper.readValue(json, BasicExpression.class);
        Assert.assertNotNull(basicExpression1);
    }

}
