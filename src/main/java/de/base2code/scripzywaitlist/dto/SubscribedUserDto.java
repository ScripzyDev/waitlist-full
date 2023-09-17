package de.base2code.scripzywaitlist.dto;

import de.base2code.scripzywaitlist.config.WebSettings;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class SubscribedUserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    private String referral;
    private boolean active = false;
    private String token = UUID.randomUUID().toString();

    private long timestamp = System.currentTimeMillis();
}
