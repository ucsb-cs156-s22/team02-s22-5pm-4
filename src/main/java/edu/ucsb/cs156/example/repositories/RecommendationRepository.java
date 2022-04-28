package edu.ucsb.cs156.example.repositories;

import org.springframework.stereotype.Repository;
import edu.ucsb.cs156.example.entities.Recommendation;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface RecommendationRepository extends CrudRepository<Recommendation, Long>{
    
}