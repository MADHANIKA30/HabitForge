package com.habitforge.forge.dto;
import com.habitforge.forge.entity.Frequency; import java.time.LocalDate;
public record StreakResponse(Long habitId,Frequency frequency,int currentStreak,int bestStreak,LocalDate lastCompletedDate,boolean currentlyActive) {}
