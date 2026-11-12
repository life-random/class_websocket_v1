package com.spring.class_websocket_v1.stomp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("SELECT c FROM Chat c WHERE c.roomId = :roomId ORDER BY c.id ASC")
    List<Chat> findByRoomId(@Param("roomId") Integer roomId);

}
