package com.habitforge.forge.repository;
import com.habitforge.forge.entity.CompletionLog; import org.springframework.data.jpa.repository.JpaRepository; import java.time.LocalDate; import java.util.List;
public interface CompletionLogRepository extends JpaRepository<CompletionLog,Long>{ boolean existsByHabitIdAndCompletedDate(Long habitId,LocalDate date); List<CompletionLog> findByHabitIdOrderByCompletedDateAsc(Long habitId);
    void deleteByHabitId(Long habitId); List<CompletionLog> findByHabitIdAndCompletedDateBetweenOrderByCompletedDateAsc(Long habitId,LocalDate from,LocalDate to); }
