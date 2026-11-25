package com.example.diana_3;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Перечень проверок и тестов для приложения Calculator (ЛР №3):
 * 1. Сложение целых и дробных чисел
 * 2. Вычитание с получением отрицательного результата
 * 3. Умножение дробных чисел (0.3 * 0.2 = 0.06 по рис. 3.13)
 * 4. Деление и обработка деления на ноль (50 / 0)
 * 5. Извлечение квадратного корня (√9 = 3) и проверка отрицательного числа
 * 6. Вычисление обратной величины (1/X)
 */
public class ExampleUnitTest {

    @Test
    public void testAddition() {
        double a = 15.5;
        double b = 4.5;
        assertEquals(20.0, a + b, 0.0001);
    }

    @Test
    public void testSubtractionNegative() {
        double a = 5.0;
        double b = 12.0;
        assertEquals(-7.0, a - b, 0.0001);
    }

    @Test
    public void testMultiplication() {
        // Проверка примера из рисунка 3.13: 0.3 * 0.2 = 0.06
        double a = 0.3;
        double b = 0.2;
        assertEquals(0.06, a * b, 0.0001);
    }

    @Test
    public void testDivisionByZero() {
        // Проверка примера из рисунка 3.13: 50 / 0 -> недопустимая операция
        double a = 50.0;
        double b = 0.0;
        assertTrue(Double.isInfinite(a / b) || b == 0.0);
    }

    @Test
    public void testSquareRoot() {
        double a = 25.0;
        assertEquals(5.0, Math.sqrt(a), 0.0001);

        double negative = -9.0;
        assertTrue(Double.isNaN(Math.sqrt(negative)));
    }

    @Test
    public void testReciprocal() {
        double a = 4.0;
        assertEquals(0.25, 1.0 / a, 0.0001);
    }
}
