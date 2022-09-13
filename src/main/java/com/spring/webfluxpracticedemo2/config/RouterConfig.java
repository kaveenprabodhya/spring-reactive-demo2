package com.spring.webfluxpracticedemo2.config;

import com.spring.webfluxpracticedemo2.dto.InputFailedValidationResponse;
import com.spring.webfluxpracticedemo2.exceptions.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
public class RouterConfig {

    @Autowired
    private RequestHandler requestHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter(){
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

//    @Bean
    private RouterFunction<ServerResponse> serverResponseRouterFunction(){
        return RouterFunctions.route()
//                .GET("square/{input}", requestHandler::squareHandler)
                .GET("square/{input}",
                        RequestPredicates
                                .path("*/1?")
                                .or(RequestPredicates.path("*/20")),
                        requestHandler::squareHandler)
                .GET("square/{input}", request -> ServerResponse.badRequest().bodyValue("only 10 -20 allowed!"))
                .GET("table/{input}", requestHandler::tableHandler)
                .GET("table/{input}/stream", requestHandler::tableStreamHandler)
                .POST("multiply", requestHandler::multiplyHandler)
                .GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler(){
        return (err, req) -> {
            InputValidationException exception = (InputValidationException) err;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setInput(exception.getInput());
            response.setErrorCode(exception.getErrorCode());
            response.setMessage(exception.getMessage());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
