package com.example.controller;
import org.springframework.web.bind.annotation.*;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import org.springframework.http.HttpStatus;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {


    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
      }
    
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAll() {

        List<Message> messages = messageService.findAllMessages();
        return ResponseEntity.ok(messages);
      }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginVerify(@RequestBody Account account){
        return accountService.login(account);
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account newAccount) {
        return accountService.createAccount(newAccount);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateMessage(@PathVariable int messageId, @RequestBody Message newMessageText) {
        try{
            int rows = messageService.updateMessageById(messageId, newMessageText);

            if(rows > 0){
                return ResponseEntity.ok("" + rows);
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
            }
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) {
    return messageService.createMessage(newMessage);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
    return messageService.findMessageById(messageId);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable("messageId") Integer messageId) {
        try{
            int rowsUpdated = messageService.delete(messageId);

            if(rowsUpdated > 0){
                return ResponseEntity.ok("" + rowsUpdated);
            }
            else{
                return ResponseEntity.ok("");
            }
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.ok("");
        }
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUserId(@PathVariable Integer accountId) {
        return messageService.findMessagesByAccountId(accountId);
    }
}
