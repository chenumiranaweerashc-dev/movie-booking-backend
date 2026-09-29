package movie_booking_system.controllers;

import movie_booking_system.entities.Showtime;
import movie_booking_system.repositories.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowtimeController {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    // POST /api/shows
    @PostMapping
    public ResponseEntity<Showtime> createShowtime(@RequestBody Showtime showtime) {
        Showtime savedShowtime = showtimeRepository.save(showtime);
        return new ResponseEntity<>(savedShowtime, HttpStatus.CREATED);
    }

    // GET /api/shows
    @GetMapping
    public ResponseEntity<List<Showtime>> getAllShowtimes() {
        return ResponseEntity.ok(showtimeRepository.findAll());
    }

    // GET /api/shows/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Showtime> getShowtimeById(@PathVariable Long id) {
        return showtimeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/shows/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Showtime> updateShowtime(@PathVariable Long id, @RequestBody Showtime details) {
        return showtimeRepository.findById(id).map(showtime -> {
            showtime.setMovie(details.getMovie());
            showtime.setTheatre(details.getTheatre());
            showtime.setStartTime(details.getStartTime());
            showtime.setTicketPrice(details.getTicketPrice());
            return ResponseEntity.ok(showtimeRepository.save(showtime));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/shows/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long id) {
        if (showtimeRepository.existsById(id)) {
            showtimeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

