package com.example.inventory.service;

import com.example.inventory.entity.Things;
import org.json.simple.JSONObject;
import com.example.inventory.jms.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class SendServiceImp implements SendService {

    private final Producer producer;

    @Autowired
    public SendServiceImp(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void sendThingChange(String action, UUID id, String name,
                 String location, String description, String category,
                         Integer quantity, String dateEnd) {

        JSONObject message = new JSONObject();
        message.put("id", id.toString());
        message.put("operation", action);
        message.put("description", description);
        message.put("name", name);
        message.put("location", location);
        message.put("category", category);
        message.put("quantity", quantity);
        message.put("dateEnd", dateEnd);

        System.out.println("[ThingServiceImpl] Sending message to topic '" + THING_CHANGE_TOPIC + "' message=" + message);
        producer.sendMessage(THING_CHANGE_TOPIC, message.toJSONString());
    }

    @Override
    public void sendStatusChange(String action, Things things) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        StringBuilder message = new StringBuilder("[")
                .append(LocalDateTime.now().format(format))
                .append("] Thing ")
                .append(action)
                .append(", ")
                .append(things.toString());

        System.out.println("[ThingServiceImpl] Sending message to topic '" + STATUS_CHANGE_TOPIC + "' message=" + message);
        producer.sendMessage(STATUS_CHANGE_TOPIC, message.toString());
    }
}
