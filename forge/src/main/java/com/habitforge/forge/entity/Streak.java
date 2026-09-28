package com.habitforge.forge.entity;
import jakarta.persistence.*; import java.time.LocalDate; import java.time.LocalDateTime;
@Entity @Table(name="streaks", uniqueConstraints=@UniqueConstraint(name="uk_streak_habit",columnNames="habit_id"))
public class Streak {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @OneToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="habit_id",nullable=false,unique=true) private Habit habit;
 @Column(nullable=false) private int currentStreak; @Column(nullable=false) private int bestStreak; private LocalDate lastCompletedDate; private LocalDateTime updatedAt;
 @PrePersist @PreUpdate void touch(){updatedAt=LocalDateTime.now();}
 public Long getId(){return id;} public Habit getHabit(){return habit;} public void setHabit(Habit v){habit=v;} public int getCurrentStreak(){return currentStreak;} public void setCurrentStreak(int v){currentStreak=v;} public int getBestStreak(){return bestStreak;} public void setBestStreak(int v){bestStreak=v;} public LocalDate getLastCompletedDate(){return lastCompletedDate;} public void setLastCompletedDate(LocalDate v){lastCompletedDate=v;} public LocalDateTime getUpdatedAt(){return updatedAt;}
}
