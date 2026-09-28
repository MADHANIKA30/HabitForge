package com.habitforge.forge.service;

import com.habitforge.forge.entity.CompletionLog;
import com.habitforge.forge.entity.Frequency;
import com.habitforge.forge.entity.Habit;
import com.habitforge.forge.entity.Streak;
import com.habitforge.forge.repository.CompletionLogRepository;
import com.habitforge.forge.repository.StreakRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class StreakService {

    private final StreakRepository streakRepository;
    private final CompletionLogRepository completionRepository;
    private final NotificationService notificationService;

    public StreakService(
            StreakRepository streakRepository,
            CompletionLogRepository completionRepository,
            NotificationService notificationService) {
        this.streakRepository = streakRepository;
        this.completionRepository = completionRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Streak refresh(Habit habit) {
        List<CompletionLog> logs = completionRepository
                .findByHabitIdOrderByCompletedDateAsc(habit.getId());

        Streak streak = streakRepository.findByHabitId(habit.getId())
                .orElseGet(() -> {
                    Streak newStreak = new Streak();
                    newStreak.setHabit(habit);
                    return newStreak;
                });

        int oldCurrent = streak.getCurrentStreak();
        int best = calculateBest(logs, habit.getFrequency());
        int current = calculateCurrent(logs, habit.getFrequency(), LocalDate.now());

        streak.setCurrentStreak(current);
        streak.setBestStreak(Math.max(streak.getBestStreak(), best));

        if (!logs.isEmpty()) {
            streak.setLastCompletedDate(logs.get(logs.size() - 1).getCompletedDate());
        }

        Streak saved = streakRepository.save(streak);

        if (oldCurrent != current) {
            notificationService.streakChanged(
                    habit, oldCurrent, current, saved.getBestStreak());
        }

        return saved;
    }

    public int calculateBest(List<CompletionLog> logs, Frequency frequency) {
        if (logs.isEmpty()) {
            return 0;
        }

        List<LocalDate> dates = dates(logs);
        int best = 1;
        int run = 1;

        for (int i = 1; i < dates.size(); i++) {
            boolean consecutive = frequency == Frequency.DAILY
                    ? dates.get(i).equals(dates.get(i - 1).plusDays(1))
                    : nextWeek(dates.get(i - 1), dates.get(i));

            if (consecutive) {
                run++;
            } else {
                run = 1;
            }

            best = Math.max(best, run);
        }

        return best;
    }

    public int calculateCurrent(
            List<CompletionLog> logs,
            Frequency frequency,
            LocalDate today) {

        if (logs.isEmpty()) {
            return 0;
        }

        List<LocalDate> dates = dates(logs);
        LocalDate last = dates.get(dates.size() - 1);

        if (frequency == Frequency.DAILY) {
            if (last.isBefore(today.minusDays(1))) {
                return 0;
            }
        } else if (weekStart(last).isBefore(weekStart(today).minusWeeks(1))) {
            return 0;
        }

        int run = 1;

        for (int i = dates.size() - 1; i > 0; i--) {
            if (frequency == Frequency.DAILY) {
                if (dates.get(i - 1).equals(dates.get(i).minusDays(1))) {
                    run++;
                } else {
                    break;
                }
            } else {
                if (nextWeek(dates.get(i - 1), dates.get(i))) {
                    run++;
                } else {
                    break;
                }
            }
        }

        return run;
    }

    private List<LocalDate> dates(List<CompletionLog> logs) {
        return logs.stream()
                .map(log -> log.getCompletedDate())
                .distinct()
                .sorted()
                .toList();
    }

    private boolean nextWeek(LocalDate first, LocalDate second) {
        return weekStart(second).equals(weekStart(first).plusWeeks(1));
    }

    private LocalDate weekStart(LocalDate date) {
        return date.with(DayOfWeek.MONDAY);
    }
}
