package com.FLsolutions.schoolTrack.repositories;
import com.FLsolutions.schoolTrack.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
