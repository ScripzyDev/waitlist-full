package de.base2code.scripzywaitlist.repository;

import de.base2code.scripzywaitlist.dto.SubscribedUserDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<SubscribedUserDto, Long> {
    public SubscribedUserDto findByEmail(String email);
}
