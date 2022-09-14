package com.spring.webfluxpracticedemo2;

import com.spring.webfluxpracticedemo2.dto.MultiplyRequestDTO;
import com.spring.webfluxpracticedemo2.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class PostRequestTest extends BaseIT {
    @Test
    public void postTest(){
        Mono<Response> responseMono = this.webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 2))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private MultiplyRequestDTO buildRequestDto(int a, int b){
        MultiplyRequestDTO requestDTO = new MultiplyRequestDTO();
        requestDTO.setFirst(a);
        requestDTO.setSecond(b);
        return requestDTO;
    }
}
