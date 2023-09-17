package de.base2code.scripzywaitlist.service;

import de.base2code.scripzywaitlist.dto.SubscribedUserDto;
import de.base2code.scripzywaitlist.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDatabase {
    @Autowired
    private UserRepository userRepository;

    public boolean isEmailInDatabase(String email) {
        // Use userRepository to check if email is already in database
        SubscribedUserDto subscribedUserDto = userRepository.findByEmail(email);
        return subscribedUserDto != null;
    }

    public SubscribedUserDto addEmailToDatabase(String email, String referral) {
        SubscribedUserDto user = new SubscribedUserDto();
        user.setEmail(email);
        user.setReferral(referral);
        userRepository.save(user);
        log.info("Added " + email + " to database with referral " + referral);
        return user;
    }

    public SubscribedUserDto getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void save(SubscribedUserDto user) {
        userRepository.save(user);
    }

}
