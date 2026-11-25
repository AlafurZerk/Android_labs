package com.example.lab1;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void testSquareAreaEqualsPerimeter() {
        int side = 4;
        int area = side * side;
        int perimeter = 4 * side;
        assertEquals(area, perimeter);
        assertEquals(16, area);
    }

    @Test
    public void testSideScale() {
        int unitScale = 60;
        int sideUnits = 4;
        int pixelSide = sideUnits * unitScale;
        assertTrue(pixelSide >= 150);
        assertEquals(240, pixelSide);
    }
}
