package kz.bitlab.main_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)*/
    //private List<Course> course;
}
