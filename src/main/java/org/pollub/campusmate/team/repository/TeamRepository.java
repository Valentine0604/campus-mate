package org.pollub.campusmate.team.repository;

import org.pollub.campusmate.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Collection<Team> findAllByCreatorId(Long creatorId);
}
