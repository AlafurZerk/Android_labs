package com.example.lab2;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void testLetterNButtonCount() {
        int leftBarButtons = 7;
        int rightBarButtons = 7;
        int middleBarButtons = 3;
        int totalButtons = leftBarButtons + rightBarButtons + middleBarButtons;
        assertEquals(17, totalButtons);
    }

    @Test
    public void testGridDimensions() {
        int rowCount = 7;
        int columnCount = 5;
        assertTrue(rowCount > 0);
        assertTrue(columnCount > 0);
        assertEquals(35, rowCount * columnCount);
    }
}
