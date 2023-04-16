package com.example.inventory.service;

import com.example.inventory.entity.Things;

import java.util.UUID;

public interface SendService {
    String STATUS_CHANGE_TOPIC = "thingChangeStatus";
    String THING_CHANGE_TOPIC = "thingChange";

    void sendStatusChange(String action, Things things);

    void sendThingChange(UUID id, String action, String description);
}
