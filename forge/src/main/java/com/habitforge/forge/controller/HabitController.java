package com.habitforge.forge.controller;
import com.habitforge.forge.dto.*; import com.habitforge.forge.service.HabitService; import io.swagger.v3.oas.annotations.Operation; import io.swagger.v3.oas.annotations.tags.Tag; import jakarta.validation.Valid; import org.springframework.data.domain.*; import org.springframework.data.web.PageableDefault; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/habits") @Tag(name="Habits",description="Habit tracking API") public class HabitController { private final HabitService service; public HabitController(HabitService s){service=s;}
 @PostMapping @Operation(summary="Create a habit") public ResponseEntity<HabitResponse> create(@Valid @RequestBody HabitRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));}
 @GetMapping @Operation(summary="List habits with pagination and sorting") public Page<HabitResponse> list(@RequestParam(defaultValue="true")boolean activeOnly,@PageableDefault(size=10,sort="createdAt",direction=Sort.Direction.DESC)Pageable p){return service.list(activeOnly,p);}
 @GetMapping("/{id}") @Operation(summary="Get one habit") public HabitResponse get(@PathVariable Long id){return service.one(id);}
 @PutMapping("/{id}") @Operation(summary="Update a habit") public HabitResponse update(@PathVariable Long id,@Valid @RequestBody HabitRequest r){return service.update(id,r);}
 @DeleteMapping("/{id}") @Operation(summary="Delete a habit") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){service.delete(id);}
 @PostMapping("/{id}/check-ins") @Operation(summary="Record a completion") public ResponseEntity<CompletionResponse> checkIn(@PathVariable Long id,@Valid @RequestBody CompletionRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.checkIn(id,r));}
 @GetMapping("/{id}/logs") @Operation(summary="Get completion history") public List<CompletionResponse> logs(@PathVariable Long id){return service.logs(id);}
 @GetMapping("/{id}/streak") @Operation(summary="Get current and best streak") public StreakResponse streak(@PathVariable Long id){return service.streak(id);}
 @GetMapping("/{id}/calendar") @Operation(summary="Get monthly completion calendar") public CalendarResponse calendar(@PathVariable Long id,@RequestParam int year,@RequestParam int month){return service.calendar(id,year,month);}
}
