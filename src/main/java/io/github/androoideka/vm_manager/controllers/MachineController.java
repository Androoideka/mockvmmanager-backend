package io.github.androoideka.vm_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.androoideka.vm_manager.model.MachineOperation;
import io.github.androoideka.vm_manager.requests.MachineRequest;
import io.github.androoideka.vm_manager.responses.MachineResponse;
import io.github.androoideka.vm_manager.services.MachineService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/machine")
public class MachineController {
    private final MachineService machineService;

    @Autowired
    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MachineResponse> create(@RequestBody MachineRequest machineRequest) {
        return ResponseEntity.ok(this.machineService.create(machineRequest));
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<MachineResponse>> search(@RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "STOPPED,RUNNING") List<String> status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dateTo,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(this.machineService.search(name, status, dateFrom, dateTo, page, size));
    }

    @GetMapping(value = "/start/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> start(@PathVariable("id") Long machineId,
            @RequestParam(required = false) String cron) {
        if (cron != null) {
            this.machineService.scheduleOperation(machineId, MachineOperation.START, cron);
        }
        this.machineService.executeOperation(machineId, MachineOperation.START);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/stop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> stop(@PathVariable("id") Long machineId,
            @RequestParam(required = false) String cron) {
        if (cron != null) {
            this.machineService.scheduleOperation(machineId, MachineOperation.STOP, cron);
        }
        this.machineService.executeOperation(machineId, MachineOperation.STOP);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/restart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> restart(@PathVariable("id") Long machineId,
            @RequestParam(required = false) String cron) {
        if (cron != null) {
            this.machineService.scheduleOperation(machineId, MachineOperation.RESTART, cron);
        }
        this.machineService.executeOperation(machineId, MachineOperation.RESTART);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/destroy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> destroy(@PathVariable("id") Long machineId) {
        this.machineService.destroy(machineId);
        return ResponseEntity.noContent().build();
    }
}
