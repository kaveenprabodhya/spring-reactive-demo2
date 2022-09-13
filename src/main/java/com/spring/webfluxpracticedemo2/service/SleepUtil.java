package com.spring.webfluxpracticedemo2.service;

public class SleepUtil {
    public static void sleepSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds);
    }
}
