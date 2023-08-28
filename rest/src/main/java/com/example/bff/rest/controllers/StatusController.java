package com.example.bff.rest.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;


@RestController
//@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusController {


    @GetMapping("/state")
    public String status() throws IOException {

        URL url = new URL("http://localhost:8083/actuator/health");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        String contentType = con.getHeaderField("Content-Type");
        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        //in.close();
        con.disconnect();

        ObjectMapper mapper = new ObjectMapper();
//        JsonNode nodes = mapper.valueToTree(content);
        JsonNode nodes=mapper.readTree(String.valueOf(content));
        String test = nodes.get("status").asText();
        return test;

//        StringBuilder sb = new StringBuilder();
//        sb.append(content);
//        return sb.toString();

    }

}
