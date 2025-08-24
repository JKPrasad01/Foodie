//package com.example.FoodApp.service.ServiceImpl;
//
//import com.example.FoodApp.service.Service.MailSenderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class MailSenderServiceImpl implements MailSenderService {
//    private final JavaMailSender javaMailSender;
//
//
//    @Override
//    public void sendSimpleEmail(String toEmail) {
//        //if the payment is success do this
//
//        if(true){
//            String subject = "Order Confirmation - FoodApp";
//
//            String body = STR."""
//            Hello \{customerName},
//
//            Thank you for placing your order with FoodApp! \uD83C\uDF89
//
//            Here are your order details:
//
//            Order ID: #\{orderId}
//            Order Date: July 26, 2025
//            Estimated Delivery: 45 minutes
//
//            Items Ordered:
//            - 1x Chicken Biryani
//            - 2x Butter Naan
//            - 1x Paneer Tikka Masala
//
//            Total Amount: ₹450.00
//            Payment Method: Online (UPI)
//
//            Your food is being prepared and will be delivered to the following address:
//            123, Main Street, City Name, State - 000000
//
//            If you have any questions or wish to make changes, feel free to contact our support at support@foodapp.com.
//
//            Thank you for choosing FoodApp!
//            We hope you enjoy your meal \uD83C\uDF7D️
//
//            Warm regards,
//            The FoodApp Team""";
//        }
//
//
//        SimpleMailMessage message=new SimpleMailMessage();
//        message.setFrom("prasadjk2241@gmail.com");
//        message.setSubject(subject);
//        message.setTo(toEmail);
//        message.setText(body);
//        javaMailSender.send(message);
//    }
//
//}
