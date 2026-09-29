package movie_booking_system.entities;

import movie_booking_system.enums.MovieStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String genre;
    private Integer durationMinutes;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private MovieStatus status;
}

