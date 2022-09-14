package com.spring.webfluxpracticedemo2;

import com.spring.webfluxpracticedemo2.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class GetSingleResponseTest extends BaseIT{
    @Autowired
    private WebClient webClient;

    @Test
    public void blockTest(){
         Response response = this.webClient
                .get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        System.out.println(response);
    }
}
