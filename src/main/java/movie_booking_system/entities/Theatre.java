package movie_booking_system.entities;

import movie_booking_system.enums.TheatreStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "theatres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String location;
    private Integer totalSeats;

    @Enumerated(EnumType.STRING)
    private TheatreStatus status;
}
