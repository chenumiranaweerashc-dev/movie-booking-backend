package movie_booking_system.controllers;

import movie_booking_system.entities.Booking;
import movie_booking_system.entities.Payment;
import movie_booking_system.repositories.BookingRepository;
import movie_booking_system.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // POST /api/payments
    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
// 1. Verify booking field exists
        if (payment.getBooking() == null || payment.getBooking().getId() == null) {
            return ResponseEntity.badRequest().body("Booking ID is required");
        }

        Long bookingId = payment.getBooking().getId();
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Booking with ID " + bookingId + " does not exist in database");
        }

// 2. Attach fully populated booking entity
        payment.setBooking(booking);

// 3. Set timestamp if omitted
        if (payment.getPaymentDate() == null) {
            payment.setPaymentDate(LocalDateTime.now());
        }

        Payment savedPayment = paymentRepository.save(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
    }

    // GET /api/payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentRepository.findAll());
    }

    // GET /api/payments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

