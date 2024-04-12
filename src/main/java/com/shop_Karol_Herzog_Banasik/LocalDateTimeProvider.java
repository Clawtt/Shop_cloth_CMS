package com.shop_Karol_Herzog_Banasik;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeProvider {

//    public static LocalDateTime getCurrentTime() {
//        return LocalDateTime.now();
//    }
    public  LocalDateTime currentTime() {
        return LocalDateTime.now();
    }

}
