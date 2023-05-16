package ru.klimov.vacationcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;

@SpringBootApplication
public class VacationCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(VacationCalculatorApplication.class, args);
    }
}

@RestController
class VacationCalculatorController {
    @GetMapping("/calculate")
    public double calculateVacation(
            @RequestParam("averageSalary") double averageSalary,
            @RequestParam("vacationDays") int vacationDays,
            @RequestParam(value = "vacationStartDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vacationStartDate) {

        if (vacationStartDate != null) {
            // Расчет отпускных с учетом точных дней ухода в отпуск
            LocalDate today = LocalDate.now();
            LocalDate vacationEndDate = LocalDate.from(vacationStartDate).plusDays(vacationDays - 1);
            long workingDays = calculateWorkingDays(vacationStartDate, vacationEndDate);
            double totalVacationPayment = averageSalary * vacationDays * 12 / 365;
            double partialVacationPayment =  workingDays * totalVacationPayment / vacationDays;
            return partialVacationPayment;
        } else {
            // Расчет отпускных без учета точных дней ухода в отпуск
            double totalVacationPayment = averageSalary * vacationDays * 12 / 365;
            return totalVacationPayment;
        }
    }

    private long calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        long workingDays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            // Проверяем, является ли текущий день рабочим днем (например, не выходной и не праздник)
            if (isWorkingDay(date)) {
                workingDays++;
            }
            date = date.plusDays(1);
        }
        return workingDays;
    }

    private boolean isWorkingDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }
}
