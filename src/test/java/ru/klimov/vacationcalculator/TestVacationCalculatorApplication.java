package ru.klimov.vacationcalculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TestVacationCalculatorApplication {
    @Test
    void testCalculateVacation() {
        VacationCalculatorController calculator = new VacationCalculatorController();
        double result;

        // Проверяем отпускные для ср.зп. = 100000 и отпуска в 10 дней
        result = calculator.calculateVacation(100000, 10, null);
        Assertions.assertEquals(32876.71232876712, result);

        // Проверяем отпускные для ср.зп. = 100000 и отпуска в 10 дней с 2023.04.26
        result = calculator.calculateVacation(100000, 10, LocalDate.of(2023, 4, 26));
        Assertions.assertEquals(26301.369863013697, result);
    }

    @Test
    void testCalculateWorkingDays() {
        VacationCalculatorController calculator = new VacationCalculatorController();
        LocalDate startDate;
        LocalDate endDate;
        long result;

        // Проверяем, что с 2023.05.03 по 2023.05.10 число рабочих дней 6
        startDate = LocalDate.of(2023, 5, 3);
        endDate = LocalDate.of(2023, 5, 10);
        result = calculator.calculateWorkingDays(startDate, endDate);
        Assertions.assertEquals(6, result);

        // Проверяем, что с 2023.04.25 по 2023.05.6 число рабочих дней 9
        startDate = LocalDate.of(2023, 4, 25);
        endDate = LocalDate.of(2023, 5, 6);
        result = calculator.calculateWorkingDays(startDate, endDate);
        Assertions.assertEquals(9, result);
    }

    @Test
    void testIsWorkingDay() {
        VacationCalculatorController calculator = new VacationCalculatorController();

        // Проверяем, что понедельник - рабочий день
        LocalDate monday = LocalDate.of(2023, 5, 22);
        Assertions.assertTrue(calculator.isWorkingDay(monday));

        // Проверяем, что среда - рабочий день
        LocalDate wednesday = LocalDate.of(2023, 5, 22);
        Assertions.assertTrue(calculator.isWorkingDay(wednesday));

        // Проверяем, что суббота - не рабочий день
        LocalDate saturday = LocalDate.of(2023, 5, 27);
        Assertions.assertFalse(calculator.isWorkingDay(saturday));

        // Проверяем, что воскресенье - не рабочий день
        LocalDate sunday = LocalDate.of(2023, 5, 28);
        Assertions.assertFalse(calculator.isWorkingDay(sunday));
    }
}
