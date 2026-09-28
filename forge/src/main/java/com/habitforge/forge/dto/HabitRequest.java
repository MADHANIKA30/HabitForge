package com.habitforge.forge.dto;
import com.habitforge.forge.entity.Frequency; import jakarta.validation.constraints.*;
public record HabitRequest(@NotBlank @Size(max=120) String name,@Size(max=500) String description,@NotNull Frequency frequency,boolean remindersEnabled,@Size(max=80) String reminderTime) {}
