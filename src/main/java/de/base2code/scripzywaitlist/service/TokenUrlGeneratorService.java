package de.base2code.scripzywaitlist.service;

import de.base2code.scripzywaitlist.config.WebSettings;
import de.base2code.scripzywaitlist.dto.SubscribedUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenUrlGeneratorService {
    @Autowired
    private WebSettings webSettings;

    public String generateTokenUrl(SubscribedUserDto subscribedUserDto) {
        return webSettings.getBaseUrl() + "/token?token=" + subscribedUserDto.getToken() + "&email=" + subscribedUserDto.getEmail();
    }

}
