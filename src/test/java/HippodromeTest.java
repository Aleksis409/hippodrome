import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {
    Hippodrome hippodrome;

    @Test
    void hippodromeExceptionHorsesListNullTest() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void hippodromeExceptionHorsesListNullMessageTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void hippodromeExceptionHorsesListEmptyTest() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    void hippodromeExceptionHorsesListEmptyMessageTest() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesOkTest() {
        int horseCount = 30;
        List<Horse> expectedHorsesList = new ArrayList<>();
        for (int i = 0; i < horseCount; i++) {
            expectedHorsesList.add(Mockito.mock(Horse.class));
        }
        hippodrome = new Hippodrome(expectedHorsesList);
        List<Horse> actualHorsesList = hippodrome.getHorses();
        assertEquals(expectedHorsesList, actualHorsesList);
    }

    @Test
    void moveOkTest() {
        int horseCount = 50;
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < horseCount; i++) {
            horseList.add(Mockito.mock(Horse.class));
        }
        hippodrome = new Hippodrome(horseList);
        hippodrome.move();
        for (Horse horse : horseList) {
            Mockito.verify(horse).move();
        }
    }

    @ParameterizedTest (name = "{index} - {0} - {1}")
    @MethodSource ("getWinnerTestArguments")
    void getWinnerOkTest(List<Horse> horseList, Horse horseMaxDistance) {
        hippodrome = new Hippodrome(horseList);
        assertEquals(horseMaxDistance, hippodrome.getWinner());
    }

    private static Stream<Arguments> getWinnerTestArguments(){
        Horse horse1 = new Horse("Horse1", 1, 1);
        Horse horse2 = new Horse("Horse2", 2, 2);
        Horse horse3 = new Horse("Horse3", 3, 3);
        List<Horse> horseList1 = List.of(horse1, horse2, horse3);

        List<Horse> horseList2 = List.of(horse2, horse3, horse1);

        List<Horse> horseList3= List.of(horse3, horse2, horse1);

        return Stream.of(
                Arguments.of(horseList1, horse3),
                Arguments.of(horseList2, horse3),
                Arguments.of(horseList3, horse3)
        );
    }
}