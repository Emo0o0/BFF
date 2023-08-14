package com.example.bff.domain;

import com.example.zoostore.restexport.ZooStoreRestClient;
import com.example.zoostorestorage.restexport.ZooStorageRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ZooStorageRestClientFactory {

    @Bean
    ZooStorageRestClient getZooStorageRestClient() {
        final ObjectMapper objectMapper = new ObjectMapper();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(ZooStorageRestClient.class, "http://localhost:8081");
    }
}
