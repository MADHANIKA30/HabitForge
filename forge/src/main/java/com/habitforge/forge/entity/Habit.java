package com.habitforge.forge.entity;
import jakarta.persistence.*; import java.time.LocalDateTime;
@Entity @Table(name="habits", indexes=@Index(name="idx_habit_name", columnList="name"))
public class Habit {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=120) private String name;
 @Column(length=500) private String description;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=10) private Frequency frequency;
 @Column(nullable=false) private boolean active=true;
 @Column(nullable=false) private boolean remindersEnabled=false;
 @Column(length=80) private String reminderTime;
 @Column(nullable=false,updatable=false) private LocalDateTime createdAt;
 @Column(nullable=false) private LocalDateTime updatedAt;
 @PrePersist void onCreate(){var now=LocalDateTime.now();createdAt=now;updatedAt=now;} @PreUpdate void onUpdate(){updatedAt=LocalDateTime.now();}
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public Frequency getFrequency(){return frequency;} public void setFrequency(Frequency v){frequency=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;} public boolean isRemindersEnabled(){return remindersEnabled;} public void setRemindersEnabled(boolean v){remindersEnabled=v;} public String getReminderTime(){return reminderTime;} public void setReminderTime(String v){reminderTime=v;} public LocalDateTime getCreatedAt(){return createdAt;} public LocalDateTime getUpdatedAt(){return updatedAt;}
}
