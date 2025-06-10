import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;

class HorseTest {
    Horse horse;

    @Test
    void horseExceptionNullNameTest() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1.0, 2.0));
    }

    @Test
    void horseExceptionMessageNullNameTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 1.0, 2.0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void horseExceptionWhenBlankOrWhitespaceName(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1.0, 2.0));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t"})
    void horseExceptionMessageWhenBlankOrWhitespaceName(String name) {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 1.0, 2.0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void horseExceptionNegativeSpeedTest() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("testHorseName", -1.0, 2.0));
    }

    @Test
    void horseExceptionMessageNegativeSpeedTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("testHorseName", -1.0, 2.0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void horseExceptionNegativeDistanceTest() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("testHorseName", 1.0, -2.0));
    }

    @Test
    void horseExceptionMessageNegativeDistanceTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("testHorseName", 1.0, -2.0));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getNameOkTest() {
        String name = "testHorseName";
        horse = new Horse(name, 1.0, 2.0);
        assertEquals(name, horse.getName());
    }

    @Test
    void getSpeedOkTest() {
        double speed = 1.0;
        horse = new Horse("testHorseName", speed, 2.0);
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    void getDistanceOkTest() {
        double distance = 2.0;
        horse = new Horse("testHorseName", 1.0, distance);
        assertEquals(distance, horse.getDistance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.5, 0.7, 0.9})
    void moveOkTest(double randomValue) {
        try (MockedStatic<Horse> horseMock = Mockito.mockStatic(Horse.class)) {
            horseMock.when(() -> Horse.getRandomDouble(anyDouble(), anyDouble())).thenReturn(randomValue);

            Horse horse = new Horse("testHorseName", 1.0, 2.0);
            double initialDistance = horse.getDistance();

            horse.move();

            horseMock.verify(() -> Horse.getRandomDouble(0.2, 0.9));

            double expectedDistance = initialDistance + horse.getSpeed() * randomValue;
            assertEquals(expectedDistance, horse.getDistance());
        }
    }

}