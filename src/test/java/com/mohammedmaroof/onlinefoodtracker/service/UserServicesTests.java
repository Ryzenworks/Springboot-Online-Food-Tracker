package com.mohammedmaroof.onlinefoodtracker.service;

import com.mohammedmaroof.onlinefoodtracker.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@SpringBootTest
public class UserServicesTests {


    @Autowired
        UserRepository userRepository;


    @ParameterizedTest
    @CsvSource({
            "Maroof",
            "Muaaz",
            "Mushfique"
    })
    public void testAdd(String name){
        assertEquals(2, 1+1);
        assertNotNull(userRepository.findByUserName(name), "failed for: " + name);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,3,5",
            "1,3,5"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a+b);
    }
}
