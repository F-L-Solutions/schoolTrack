package com.FLsolutions.schoolTrack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FLsolutions.schoolTrack.models.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long>{

}