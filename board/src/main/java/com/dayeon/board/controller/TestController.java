package com.dayeon.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/{useranme}")
    @Secured("ROLE_NOT_PERMITTED")
    public ResponseEntity<String> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok("you are not permitted");
    }
}

