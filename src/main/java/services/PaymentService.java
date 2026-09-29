package movie_booking_system.services;

import movie_booking_system.entities.Booking;
import movie_booking_system.entities.Payment;
import movie_booking_system.repositories.BookingRepository;
import movie_booking_system.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Payment processPayment(Long bookingId, Double amount, String paymentMethod) {
// 1. Fetch booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));

// 2. Create and populate Payment object via setters
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus("COMPLETED");
        payment.setPaymentDate(LocalDateTime.now());

// 3. Update Booking status to CONFIRMED
        booking.setStatus("CONFIRMED");
        bookingRepository.save(booking);

// 4. Save and return payment
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
    }
}

