package com.habitforge.forge.dto;
import com.habitforge.forge.entity.Frequency; import java.time.*;
public record HabitResponse(Long id,String name,String description,Frequency frequency,boolean active,boolean remindersEnabled,String reminderTime,int currentStreak,int bestStreak,LocalDate lastCompletedDate,LocalDateTime createdAt,LocalDateTime updatedAt) {}
