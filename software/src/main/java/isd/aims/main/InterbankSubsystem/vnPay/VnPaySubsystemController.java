package isd.aims.main.InterbankSubsystem.vnPay;

import isd.aims.main.listener.TransactionResultListener;
import isd.aims.main.entity.payment.PaymentTransaction;
import isd.aims.main.entity.request.Request;
import isd.aims.main.entity.response.Response;
import isd.aims.main.utils.Configs;
import isd.aims.main.views.payment.VNPay;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class VnPaySubsystemController {

    private static final String PAY_COMMAND = "pay";
    private static final String VERSION = "2.1.0";
    private TransactionResultListener listener;

    public VnPaySubsystemController() {
    }

    public VnPaySubsystemController(TransactionResultListener listener) {
        this.listener = listener;
    }

    public PaymentTransaction refund(int amount, String contents) {
        return null;
    }

    public String generatePayOrderUrl(int money, String contents) throws IOException {
        long amount = money * 100L * 1000;

        return VnPayConfig.buildURL(contents, amount);
    }

    public void payOrder(int amount, String orderInfo) throws IOException {
        var req = new Request(amount, orderInfo);
        String paymentURL = req.buildQueryURL();
        Stage stage = new Stage();
        var vnPayScreen = new VNPay(stage, Configs.PAYMENT_SCREEN_PATH, paymentURL, this.listener);
        vnPayScreen.show();
    }

    public static PaymentTransaction processResponse(String vnpReturnURL) throws URISyntaxException, ParseException {
        URI uri = new URI(vnpReturnURL);
        String query = uri.getQuery();
        Response response = new Response(query);

        if (response == null) return null;

        // Create Payment transaction
        String errorCode = response.getVnp_TransactionStatus();
        String transactionId = response.getVnp_TransactionNo();
        String transactionContent = response.getVnp_OrderInfo();
        long amount = Long.parseLong(response.getVnp_Amount()) / 100;
        String createdAt = response.getVnp_PayDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        Date date = dateFormat.parse(createdAt);

        return new PaymentTransaction(errorCode, transactionId, transactionContent, amount, date);
    }

}
