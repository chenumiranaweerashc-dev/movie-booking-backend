package movie_booking_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class BookingRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Showtime ID is required")
    private Long showtimeId;

    @NotNull(message = "Seats count is required")
    @Min(value = 1, message = "At least 1 seat must be booked")
    private Integer seats;

    private List<String> seatNumbers; // Optional array for specific seat numbers like ["A1", "A2"]

    // Constructors
    public BookingRequestDTO() {}

    public BookingRequestDTO(Long userId, Long showtimeId, Integer seats) {
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seats = seats;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getShowtimeId() { return showtimeId; }
    public void setShowtimeId(Long showtimeId) { this.showtimeId = showtimeId; }

    public Integer getSeats() { return seats; }
    public void setSeats(Integer seats) { this.seats = seats; }

    public List<String> getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; }
}

