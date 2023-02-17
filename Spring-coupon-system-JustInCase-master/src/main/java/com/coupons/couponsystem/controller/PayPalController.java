package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.dto.Order;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Purchase;
import com.coupons.couponsystem.service.impl.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/paypal/")
public class PayPalController extends ClientController {

    @Autowired
    PaypalService paypalService;

    public static final String SUCCESS_URL="pay/success/";
    public static final String CANCEL_URL="pay/cancel";
    public static final String DOMAIN="http://localhost:8081/api/paypal/";


    @PostMapping
    public String payment(@RequestBody Order order){
        try {
            Payment payment = paypalService.createPayment(order.getPrice(),
                    order.getCurrency(),
                    order.getMethod(),
                    order.getIntent(),
                    order.getDescription(),
                    DOMAIN + CANCEL_URL,
                    DOMAIN + SUCCESS_URL);

            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    System.out.println("the links " + link.getHref());
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

    @GetMapping("pay/cancel")
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping("pay/success/")
    public Boolean successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                long id = Long.parseLong(payment.getTransactions().get(0).getDescription());

                System.out.println(id +" is about to get saved");

               Purchase purchase= purchaseRepository.findById(id).orElseThrow(()
                        -> new CouponSystemException( "purchase was not found payment process ", HttpStatus.NOT_FOUND));

                for (Coupon coupon: purchase.getCoupons()) {

                    System.out.println(coupon.getAmount());
                    coupon.setAmount(coupon.getAmount() - 1);

                }
               purchase.setPaymentApproval(true);
                purchaseRepository.save(purchase);
                return true;
            }

        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        } catch (CouponSystemException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
