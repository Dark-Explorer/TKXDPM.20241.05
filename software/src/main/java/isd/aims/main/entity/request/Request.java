package isd.aims.main.entity.request;

import isd.aims.main.InterbankSubsystem.vnPay.VnPayConfig;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class Request {
    int money;
    String orderInfo;

    public Request(int money, String orderInfo) {
        this.money = money;
        this.orderInfo = orderInfo;
    }

    // Content Coupling
    // chỉ sử dụng một vài trường trong lớp VnPayConfig để lấy cấu hình thanh toán
    // => Sử dụng hoàn toàn VNPayConfig để tạo URL
    // FIXED
    public String buildQueryURL() throws IOException {
        long amount = money * 100L;

        return VnPayConfig.buildURL(orderInfo, amount);
    }
}
