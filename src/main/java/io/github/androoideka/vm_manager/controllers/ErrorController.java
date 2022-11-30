package io.github.androoideka.vm_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.androoideka.vm_manager.responses.ErrorLogResponse;
import io.github.androoideka.vm_manager.services.ErrorLogService;

@RestController
@RequestMapping("/error")
public class ErrorController {
    private final ErrorLogService errorLogService;

    @Autowired
    public ErrorController(ErrorLogService errorLogService) {
        this.errorLogService = errorLogService;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ErrorLogResponse>> list(@RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(this.errorLogService.listErrors(page, size));
    }
}
