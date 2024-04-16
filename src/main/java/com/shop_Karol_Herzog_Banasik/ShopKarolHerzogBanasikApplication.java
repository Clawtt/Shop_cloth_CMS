package com.shop_Karol_Herzog_Banasik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopKarolHerzogBanasikApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopKarolHerzogBanasikApplication.class, args);
        System.out.println(System.getProperty("user.dir"));
    }

}
