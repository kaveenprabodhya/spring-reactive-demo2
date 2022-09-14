package com.spring.webfluxpracticedemo2;

import com.spring.webfluxpracticedemo2.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class GetMultipleResponseTest extends BaseIT{
    @Test
    public void fluxTest(){
        Flux<Response> responseFlux = this.webClient
                .get()
                .uri("reactive-math/table/{number}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void fluxStreamTest(){
        Flux<Response> responseFlux = this.webClient
                .get()
                .uri("reactive-math/table/{number}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

}