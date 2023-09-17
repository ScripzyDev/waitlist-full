package de.base2code.scripzywaitlist.repository;

import de.base2code.scripzywaitlist.dto.SubscribedUserDto;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<SubscribedUserDto, Long> {
    public SubscribedUserDto findByEmail(String email);
}
