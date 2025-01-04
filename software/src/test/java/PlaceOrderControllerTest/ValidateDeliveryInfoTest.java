package PlaceOrderControllerTest;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ValidateAddressTest.class,
        ValidatePhoneNumberTest.class,
        ValidateNameTest.class
})
class ValidateDeliveryInfoTest {
}
