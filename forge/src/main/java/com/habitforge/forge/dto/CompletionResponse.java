package com.habitforge.forge.dto;
import java.time.*;
public record CompletionResponse(Long id,Long habitId,LocalDate completedDate,String note,LocalDateTime createdAt) {}
