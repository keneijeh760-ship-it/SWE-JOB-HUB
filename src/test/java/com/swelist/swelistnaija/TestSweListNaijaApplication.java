package com.swelist.swelistnaija;

import org.springframework.boot.SpringApplication;

public class TestSweListNaijaApplication {

    public static void main(String[] args) {
        SpringApplication.from(SweListNaijaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
