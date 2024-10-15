package org.pollub.campusmate.repository;

import org.pollub.campusmate.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

    Team findByTeamId(Long teamId);

    Team findByTeamName(String teamName);

    boolean existsByTeamId(Long teamId);

    boolean existsByTeamName(String teamName);

    void deleteByTeamId(Long teamId);

    void deleteByTeamName(String teamName);
}
