package movie_booking_system.controllers;

import movie_booking_system.entities.Theatre;
import movie_booking_system.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    @Autowired
    private TheatreRepository theatreRepository;

    // POST /api/theatres
    @PostMapping
    public ResponseEntity<Theatre> createTheatre(@RequestBody Theatre theatre) {
        Theatre savedTheatre = theatreRepository.save(theatre);
        return new ResponseEntity<>(savedTheatre, HttpStatus.CREATED);
    }

    // GET /api/theatres
    @GetMapping
    public ResponseEntity<List<Theatre>> getAllTheatres() {
        return ResponseEntity.ok(theatreRepository.findAll());
    }

    // GET /api/theatres/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Theatre> getTheatreById(@PathVariable Long id) {
        return theatreRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/theatres/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Theatre> updateTheatre(@PathVariable Long id, @RequestBody Theatre theatreDetails) {
        return theatreRepository.findById(id).map(theatre -> {
            theatre.setName(theatreDetails.getName());
            theatre.setLocation(theatreDetails.getLocation());
            theatre.setTotalSeats(theatreDetails.getTotalSeats());
            theatre.setStatus(theatreDetails.getStatus());
            return ResponseEntity.ok(theatreRepository.save(theatre));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/theatres/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheatre(@PathVariable Long id) {
        if (theatreRepository.existsById(id)) {
            theatreRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

