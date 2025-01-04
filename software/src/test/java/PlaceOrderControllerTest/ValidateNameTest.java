package PlaceOrderControllerTest;

import isd.aims.main.controller.PlaceOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidateNameTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() {
        placeOrderController = new PlaceOrderController();
    }

    static Stream<Arguments> nameProvider() {
        return Stream.of(
                Arguments.of("nguyenhuuduc", true),
                Arguments.of(null, false),
                Arguments.of("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", false),
                Arguments.of("abc_@", false)
        );
    }

    @ParameterizedTest
    @MethodSource("nameProvider")
    void validateNameTest(String name, boolean expected) {
        boolean isValid = placeOrderController.validateName(name);
        assertEquals(expected, isValid);
    }
}