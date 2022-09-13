package com.spring.webfluxpracticedemo2.service;

import java.util.concurrent.TimeUnit;

public class SleepUtil {
    public static void sleepSeconds(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
