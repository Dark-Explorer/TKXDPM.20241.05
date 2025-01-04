package PlaceOrderControllerTest;

import isd.aims.main.controller.PlaceOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidatePhoneNumberTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() {
        placeOrderController = new PlaceOrderController();
    }

    static Stream<Arguments> phoneNumberProvider() {
        return Stream.of(
                Arguments.of("a0123456789", false),
                Arguments.of("9416912", false),
                Arguments.of("9876543210", false),
                Arguments.of("0-123-456-789", true),
                Arguments.of("0-123/456.789", false),
                Arguments.of("0---123-456-789-", false),
                Arguments.of("0123456789", true)
        );
    }

    @ParameterizedTest
    @MethodSource("phoneNumberProvider")
    void validatePhoneNumberTest(String phoneNumber, boolean expected) {
        boolean isValid = placeOrderController.validatePhoneNumber(phoneNumber);
        assertEquals(expected, isValid);
    }
}