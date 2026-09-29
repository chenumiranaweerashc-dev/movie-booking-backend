package movie_booking_system.services;

import movie_booking_system.entities.Booking;
import movie_booking_system.entities.Showtime;
import movie_booking_system.entities.User;
import movie_booking_system.exceptions.BadRequestException;
import movie_booking_system.exceptions.ResourceNotFoundException;
import movie_booking_system.repositories.BookingRepository;
import movie_booking_system.repositories.ShowtimeRepository;
import movie_booking_system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Booking createBooking(Long userId, Long showtimeId, Integer seats) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with ID: " + showtimeId));

        int currentSeats = (showtime.getAvailableSeats() != null)
                ? showtime.getAvailableSeats()
                : 200;

        if (seats == null || seats <= 0) {
            throw new BadRequestException("Must book at least 1 seat");
        }

        if (seats > currentSeats) {
            throw new BadRequestException("Not enough seats available. Remaining seats: " + currentSeats);
        }

        showtime.setAvailableSeats(currentSeats - seats);
        showtimeRepository.save(showtime);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShowtime(showtime);
        booking.setSeats(seats);
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        return bookingRepository.findByUserId(userId);
    }
}

