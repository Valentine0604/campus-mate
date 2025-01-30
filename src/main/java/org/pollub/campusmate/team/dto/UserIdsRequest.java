package org.pollub.campusmate.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UserIdsRequest {
    private ArrayList<Long> userIds;
}
