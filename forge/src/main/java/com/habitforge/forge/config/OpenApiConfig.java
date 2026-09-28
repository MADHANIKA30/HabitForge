package com.habitforge.forge.config;
import io.swagger.v3.oas.models.OpenAPI; import io.swagger.v3.oas.models.info.Info; import org.springframework.context.annotation.*;
@Configuration public class OpenApiConfig { @Bean OpenAPI habitForgeOpenAPI(){return new OpenAPI().info(new Info().title("HabitForge API").version("1.0.0").description("Personal Habit Streak Tracker with reminders, completion logs, streak analytics and monthly calendars."));} }
