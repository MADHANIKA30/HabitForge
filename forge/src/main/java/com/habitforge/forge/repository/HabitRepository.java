package com.habitforge.forge.repository;
import com.habitforge.forge.entity.Habit; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.JpaRepository;
public interface HabitRepository extends JpaRepository<Habit,Long>{ Page<Habit> findByActive(boolean active,Pageable pageable); }
