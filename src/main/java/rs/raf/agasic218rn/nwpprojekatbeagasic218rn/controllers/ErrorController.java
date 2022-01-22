package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses.ErrorLogResponse;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.services.ErrorLogService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/error")
public class ErrorController {
    private final ErrorLogService errorLogService;

    @Autowired
    public ErrorController(ErrorLogService errorLogService) {
        this.errorLogService = errorLogService;
    }

    @GetMapping(value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ErrorLogResponse>> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(this.errorLogService.listErrors(page, size));
    }
}
