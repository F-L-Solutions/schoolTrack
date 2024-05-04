package com.FLsolutions.schoolTrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FLsolutions.schoolTrack.models.Kid;

public interface KidRepository extends JpaRepository<Kid, Long> {

}
