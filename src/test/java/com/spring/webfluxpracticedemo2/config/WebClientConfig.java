package com.spring.webfluxpracticedemo2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
//                .defaultHeaders(h -> h.setBasicAuth("username", "password"))
                .filter(this::sessionToken)
                .build();
    }

//    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction function){
//        System.out.println("Generating session token");
//        ClientRequest clientRequest = ClientRequest
//                .from(request)
//                .headers(httpHeaders -> httpHeaders.setBearerAuth("some-lengthy-jwt")).build();
//        return function.exchange(clientRequest);
//    }
    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction function){
        ClientRequest clientRequest = request.attribute("auth")
                .map(o -> o.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                .orElse(request);
        return function.exchange(clientRequest);
    }

    private ClientRequest withBasicAuth(ClientRequest request){
        return ClientRequest.from(request)
                .headers(httpHeaders -> httpHeaders.setBasicAuth("username", "password"))
                .build();
    }
    private ClientRequest withOAuth(ClientRequest request){
        return ClientRequest.from(request)
                .headers(httpHeaders -> httpHeaders.setBearerAuth("some-token"))
                .build();
    }
}
