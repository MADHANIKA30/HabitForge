package com.habitforge.forge.entity;
import jakarta.persistence.*; import java.time.LocalDate; import java.time.LocalDateTime;
@Entity @Table(name="completion_logs", uniqueConstraints=@UniqueConstraint(name="uk_habit_completion_date",columnNames={"habit_id","completed_date"}), indexes=@Index(name="idx_completion_habit_date",columnList="habit_id,completed_date"))
public class CompletionLog {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="habit_id",nullable=false) private Habit habit;
 @Column(name="completed_date",nullable=false) private LocalDate completedDate;
 @Column(length=500) private String note;
 @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
 @PrePersist void onCreate(){createdAt=LocalDateTime.now();}
 public Long getId(){return id;} public Habit getHabit(){return habit;} public void setHabit(Habit v){habit=v;} public LocalDate getCompletedDate(){return completedDate;} public void setCompletedDate(LocalDate v){completedDate=v;} public String getNote(){return note;} public void setNote(String v){note=v;} public LocalDateTime getCreatedAt(){return createdAt;}
}
