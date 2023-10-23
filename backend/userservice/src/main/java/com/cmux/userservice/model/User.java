package main.java.com.cmux.userservice.model;

import lombok.Data;
import javax.persistence;
import javax.annotation.processing.Generated;


@Data
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userid;

    @Column(unique = true)
    private String username;

    private String password;
}
