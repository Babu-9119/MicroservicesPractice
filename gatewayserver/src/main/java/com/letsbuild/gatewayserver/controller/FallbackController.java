package com.letsbuild.gatewayserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/contactSupport")
    public Mono<String> contactSupport(){

        return Mono.just("A technical error has been occurred, please contact our support @123456789");
    }
}
