package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.MessageRepository;
import com.example.entity.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> findAllMessages() {
        return messageRepository.findAll();
    }

    public int updateMessageById(int id, Message messageText){
        if (messageText==null || messageText.getMessageText().trim().isEmpty() || messageText.getMessageText().length()>255){
            throw new IllegalArgumentException();
        }
        Optional<Message> existingMessage = messageRepository.findById(id);

        if(existingMessage.isPresent()){
            Message message = existingMessage.get();
            message.setMessageText(message.getMessageText());
            messageRepository.save(message);
            return 1;
        }
        else{
            return 0;
        }
        }
    
    public ResponseEntity<Message> createMessage(Message newMessage){
        Optional<Message> existingAccount = messageRepository.findById(newMessage.getPostedBy());
        if(!existingAccount.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (newMessage==null || newMessage.getMessageText().trim().isEmpty() || newMessage.getMessageText().length()>255){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Message addMessage = messageRepository.save(newMessage);
        return new ResponseEntity<>(addMessage, HttpStatus.OK);
    }

    public ResponseEntity<Message> findMessageById(int messageId){
        Optional<Message> existingMessage = messageRepository.findMessageById(messageId);

        if(existingMessage.isPresent()){
            Message message = existingMessage.get();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    public Optional<Message> findMessById(int messageId){
        Optional<Message> existingMessage = messageRepository.findMessageById(messageId);

        if(existingMessage.isPresent()){
            return existingMessage;
        }
        else{
            return null;
        }
    }


    public ResponseEntity<List<Message>> findMessagesByAccountId(Integer accountId) {
        List<Message> messages = messageRepository.findByPostedBy(accountId);
        return ResponseEntity.ok(messages);
    }


    public int delete(Integer messageId){

        Optional<Message> existingMessage = messageRepository.findById(messageId);
        
        if(existingMessage.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }
        else{
            return 0;
        }
    }
}

