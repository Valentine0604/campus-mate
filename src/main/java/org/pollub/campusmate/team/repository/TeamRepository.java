package org.pollub.campusmate.team.repository;

import org.pollub.campusmate.team.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {


}
