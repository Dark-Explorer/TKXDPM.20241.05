package PlaceOrderControllerTest;

import isd.aims.main.controller.PlaceOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidateAddressTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() {
        placeOrderController = new PlaceOrderController();
    }

    static Stream<Arguments> addressProvider() {
        return Stream.of(
                Arguments.of("So 1 Dai Co Viet Hai Ba Trung Ha Noi", true),
                Arguments.of(null, false),
                Arguments.of("Lorem ipsum dolor sit amet consectetur adipiscing elit Nam commodo at nisi eu elementum Aenean placerat", false),
                Arguments.of("#60, Le Thanh Nghi, SVD Bach Khoa", false)
        );
    }

    @ParameterizedTest
    @MethodSource("addressProvider")
    void validateAddressTest(String address, boolean expected) {
        boolean isValid = placeOrderController.validateAddress(address);
        assertEquals(expected, isValid);
    }
}