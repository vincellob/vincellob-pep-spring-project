package com.example.repository;

import org.springframework.stereotype.Repository;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByPostedBy(Integer postedBy);

    @Query("SELECT m FROM Message m WHERE m.id = ?1")
    Optional<Message> findMessageById(Integer messageId);
}
