package org.pollub.campusmate.schedule.entity;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CalendarEvent {
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private String uid;
    private String summary;
    private String location;
    private String status;
}