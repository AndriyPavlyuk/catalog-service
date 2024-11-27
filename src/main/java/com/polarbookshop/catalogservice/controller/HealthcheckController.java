package com.polarbookshop.catalogservice.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import java.time.ZonedDateTime;

@RestController
class HealthcheckController {

    private final ObjectMapper objectMapper;

    HealthcheckController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/healthcheck", produces="application/json")
    public ResponseEntity<String> healthcheck(@RequestParam String format) {
        String status = "OK";
        if ("short".equals(format)) {

            String shortMessage = convertToJson(new ShortMessage(status));
            return ResponseEntity.status(HttpStatus.OK).body(shortMessage);
        }
        if (format.equals("full")) {
            String time  = ZonedDateTime.now()
                .truncatedTo( ChronoUnit.MINUTES )
                .format( DateTimeFormatter.ISO_DATE_TIME );

            Message messageFull = new FullMessage(time, status);
            String json = convertToJson(messageFull);

            return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(json);
        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    private String convertToJson(Message message) {
        try {
            return objectMapper.writeValueAsString(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping(value = "/healthcheck", produces="application/json")
    public ResponseEntity<String> healthcheckPut() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @PostMapping(value = "/healthcheck", produces="application/json")
    public ResponseEntity<String> healthcheckPost() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }


    @DeleteMapping(value = "/healthcheck", produces="application/json")
    public ResponseEntity<String> healthcheckDelete() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

}

class FullMessage implements Message {
    private String currentTime;
    private String status;

    public FullMessage(String currentTime, String status) {
        this.currentTime = currentTime;
        this.status = status;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String send() {
        return "dfdf";
    }
}

class ShortMessage implements Message {

    private String status;

    public ShortMessage(String status) {

        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String send() {
        return "fdd";
    }
}

interface Message {
    String send();
}


