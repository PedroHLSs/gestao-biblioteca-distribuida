package com.example.phls_biblioteca.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StatusController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.application.name:application}")
    private String appName;

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> body = new HashMap<>();
        body.put("status", "OK");
        body.put("application", appName);
        body.put("port", serverPort);
        return ResponseEntity.ok(body);
    }
}
