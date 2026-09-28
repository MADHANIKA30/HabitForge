package com.habitforge.forge.dto;
import java.time.LocalDate; import java.util.List;
public record CalendarResponse(Long habitId,int year,int month,List<LocalDate> completedDates) {}
