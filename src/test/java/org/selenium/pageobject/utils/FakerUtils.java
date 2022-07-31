package org.selenium.pageobject.utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    Faker faker = new Faker();

    public Long generateRandomNumber(){
        return faker.number().randomNumber(10,true);
    }
}
