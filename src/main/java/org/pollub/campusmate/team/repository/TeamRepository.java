package org.pollub.campusmate.team.repository;

import jakarta.transaction.Transactional;
import org.pollub.campusmate.team.entity.Team;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Collection<Team> findAllByCreatorId(Long creatorId);
}
