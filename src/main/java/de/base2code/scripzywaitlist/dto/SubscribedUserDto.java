package de.base2code.scripzywaitlist.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("subscribedUsers")
@Getter
@Setter
public class SubscribedUserDto {
    @Id
    private String id;

    //@Column(name = "email", nullable = false, length = 100, unique = true)
    @Indexed(unique = true)
    private String email;

    private String referral;
    private boolean active = false;
    private String token = UUID.randomUUID().toString();

    private long timestamp = System.currentTimeMillis();
}
