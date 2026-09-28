package com.habitforge.forge.service;

import com.habitforge.forge.dto.CalendarResponse;
import com.habitforge.forge.dto.CompletionRequest;
import com.habitforge.forge.dto.CompletionResponse;
import com.habitforge.forge.dto.HabitRequest;
import com.habitforge.forge.dto.HabitResponse;
import com.habitforge.forge.dto.StreakResponse;
import com.habitforge.forge.entity.CompletionLog;
import com.habitforge.forge.entity.Habit;
import com.habitforge.forge.entity.Streak;
import com.habitforge.forge.exception.ConflictException;
import com.habitforge.forge.exception.NotFoundException;
import com.habitforge.forge.repository.CompletionLogRepository;
import com.habitforge.forge.repository.HabitRepository;
import com.habitforge.forge.repository.StreakRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final CompletionLogRepository completionRepository;
    private final StreakService streakService;
    private final StreakRepository streakRepository;

    public HabitService(
            HabitRepository habitRepository,
            CompletionLogRepository completionRepository,
            StreakService streakService,
            StreakRepository streakRepository) {
        this.habitRepository = habitRepository;
        this.completionRepository = completionRepository;
        this.streakService = streakService;
        this.streakRepository = streakRepository;
    }

    @Transactional
    public HabitResponse create(HabitRequest request) {
        Habit habit = new Habit();
        apply(habit, request);
        return toResponse(habitRepository.save(habit));
    }

    @Transactional(readOnly = true)
    public Page<HabitResponse> list(boolean activeOnly, Pageable pageable) {
        Page<Habit> page = activeOnly
                ? habitRepository.findByActive(true, pageable)
                : habitRepository.findAll(pageable);
        return page.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public HabitResponse one(Long id) {
        return toResponse(find(id));
    }

    @Transactional
    public HabitResponse update(Long id, HabitRequest request) {
        Habit habit = find(id);
        apply(habit, request);
        return toResponse(habitRepository.save(habit));
    }

    @Transactional
    public void delete(Long id) {
        find(id);
        streakRepository.deleteByHabitId(id);
        completionRepository.deleteByHabitId(id);
        habitRepository.deleteById(id);
    }

    @Transactional
    public CompletionResponse checkIn(Long id, CompletionRequest request) {
        Habit habit = find(id);
        LocalDate date = request.completedDate();

        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Completion date cannot be in the future");
        }

        if (completionRepository.existsByHabitIdAndCompletedDate(id, date)) {
            throw new ConflictException("Habit is already completed for " + date);
        }

        CompletionLog log = new CompletionLog();
        log.setHabit(habit);
        log.setCompletedDate(date);
        log.setNote(request.note());

        CompletionLog saved = completionRepository.save(log);
        streakService.refresh(habit);

        return new CompletionResponse(
                saved.getId(),
                id,
                saved.getCompletedDate(),
                saved.getNote(),
                saved.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<CompletionResponse> logs(Long id) {
        find(id);
        return completionRepository.findByHabitIdOrderByCompletedDateAsc(id)
                .stream()
                .map(log -> new CompletionResponse(
                        log.getId(),
                        id,
                        log.getCompletedDate(),
                        log.getNote(),
                        log.getCreatedAt()))
                .toList();
    }

    @Transactional
    public StreakResponse streak(Long id) {
        Habit habit = find(id);
        Streak streak = streakService.refresh(habit);
        return new StreakResponse(
                id,
                habit.getFrequency(),
                streak.getCurrentStreak(),
                streak.getBestStreak(),
                streak.getLastCompletedDate(),
                streak.getCurrentStreak() > 0);
    }

    @Transactional(readOnly = true)
    public CalendarResponse calendar(Long id, int year, int month) {
        find(id);

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }

        YearMonth yearMonth = YearMonth.of(year, month);
        List<LocalDate> completedDates = completionRepository
                .findByHabitIdAndCompletedDateBetweenOrderByCompletedDateAsc(
                        id,
                        yearMonth.atDay(1),
                        yearMonth.atEndOfMonth())
                .stream()
                .map(log -> log.getCompletedDate())
                .toList();

        return new CalendarResponse(id, year, month, completedDates);
    }

    private Habit find(Long id) {
        return habitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Habit not found: " + id));
    }

    private void apply(Habit habit, HabitRequest request) {
        habit.setName(request.name().trim());
        habit.setDescription(request.description());
        habit.setFrequency(request.frequency());
        habit.setRemindersEnabled(request.remindersEnabled());
        habit.setReminderTime(request.reminderTime());
    }

    private HabitResponse toResponse(Habit habit) {
        List<CompletionLog> logs = completionRepository
                .findByHabitIdOrderByCompletedDateAsc(habit.getId());

        int current = streakService.calculateCurrent(
                logs, habit.getFrequency(), LocalDate.now());
        int best = streakService.calculateBest(logs, habit.getFrequency());
        LocalDate last = logs.isEmpty()
                ? null
                : logs.get(logs.size() - 1).getCompletedDate();

        return new HabitResponse(
                habit.getId(),
                habit.getName(),
                habit.getDescription(),
                habit.getFrequency(),
                habit.isActive(),
                habit.isRemindersEnabled(),
                habit.getReminderTime(),
                current,
                best,
                last,
                habit.getCreatedAt(),
                habit.getUpdatedAt());
    }
}
