package com.census.support.message;

import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    JSONObject findByTicketCode(String code);
}
