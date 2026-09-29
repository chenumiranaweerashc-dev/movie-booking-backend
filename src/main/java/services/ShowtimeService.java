
package movie_booking_system.services;

import movie_booking_system.entities.Movie;
import movie_booking_system.entities.Showtime;
import movie_booking_system.entities.Theatre;
import movie_booking_system.repositories.MovieRepository;
import movie_booking_system.repositories.ShowtimeRepository;
import movie_booking_system.repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    public Showtime getShowtimeById(Long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showtime not found with id: " + id));
    }

    public List<Showtime> getShowtimesByMovie(Long movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }

    public Showtime createShowtime(Long movieId, Long theatreId, Showtime showtime) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + movieId));

        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found with id: " + theatreId));

        showtime.setMovie(movie);
        showtime.setTheatre(theatre);

        if (showtime.getAvailableSeats() == null) {
            showtime.setAvailableSeats(theatre.getTotalSeats());
        }

        return showtimeRepository.save(showtime);
    }
}

