package com.spring.webfluxpracticedemo2;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.net.URI;

public class QueryParamsTest extends BaseIT {
    String queryString = "http://localhost:8080/jobs/search?count={count}&page={page}";

    @Test
    public void queryParamsTest(){
//        URI uri = UriComponentsBuilder
//                .fromUriString(queryString)
//                .build(10, 20);

        Flux<Integer> integerFlux = this.webClient
                .get()
//                .uri(uri)
                .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(10,20))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier
                .create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
