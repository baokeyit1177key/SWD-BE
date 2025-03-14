package online.gemfpt.BE.Controller;

import online.gemfpt.BE.model.ProgramRequest;
import online.gemfpt.BE.model.ProgramResponse;
import online.gemfpt.BE.service.ProgramService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @PostMapping
    public ResponseEntity<ProgramResponse> createProgram(@Valid @RequestBody ProgramRequest request) {
        return ResponseEntity.ok(programService.createProgram(request));
    }

    @GetMapping
    public ResponseEntity<List<ProgramResponse>> getAllPrograms() {
        return ResponseEntity.ok(programService.getAllPrograms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramResponse> getProgramById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getProgramById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramResponse> updateProgram(@PathVariable Long id, @Valid @RequestBody ProgramRequest request) {
        return ResponseEntity.ok(programService.updateProgram(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        programService.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/register")
    public ResponseEntity<ProgramResponse> registerProgram(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(programService.registerProgram(id, userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProgramResponse>> getProgramsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(programService.getProgramsByUserId(userId));
    }

    // Xử lý lỗi validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}