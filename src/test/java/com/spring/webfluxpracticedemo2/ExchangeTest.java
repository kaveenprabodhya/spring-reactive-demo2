package com.spring.webfluxpracticedemo2;

import com.spring.webfluxpracticedemo2.dto.InputFailedValidationResponse;
import com.spring.webfluxpracticedemo2.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ExchangeTest extends BaseIT{
    @Test
    public void badRequestTest(){
        Mono<Object> responseMono = this.webClient
                .get()
                .uri("reactive-math/square/{number}/throw", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse response){
        if(response.rawStatusCode() == 400){
            return response.bodyToMono(InputFailedValidationResponse.class);
        } else {
            return response.bodyToMono(Response.class);
        }
    }
}
