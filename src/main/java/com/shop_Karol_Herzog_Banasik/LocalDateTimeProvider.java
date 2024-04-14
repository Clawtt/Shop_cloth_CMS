package com.shop_Karol_Herzog_Banasik;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeProvider {

    public  LocalDateTime currentTime() {
        return LocalDateTime.now();
    }

}
