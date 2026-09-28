package com.habitforge.forge.dto;
import jakarta.validation.constraints.*; import java.time.LocalDate;
public record CompletionRequest(@NotNull LocalDate completedDate,@Size(max=500) String note) {}
