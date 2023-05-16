package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;


import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesOfUserHandler);
        app.post("register", this::postRegisterHandler);
        app.post("login", this::postLoginHandler);
        app.post("messages", this::postMessagesHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);
        

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    public void postRegisterHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }   
    }

    public void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedInAccount = accountService.loginAccount(account);
        if(loggedInAccount!=null){
            ctx.json(mapper.writeValueAsString(loggedInAccount));
        }else{
            ctx.status(401);
        }  
    }

    public void postMessagesHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message sentMessage = messageService.sendMessage(message);
        if(sentMessage !=null){
            ctx.json(mapper.writeValueAsString(sentMessage));
        }else{
            ctx.status(400);
        }  
    }
    public void getAllMessagesHandler(Context ctx){
        List<Message> allMessages = messageService.getAllMessages();
        ctx.json(allMessages);
    }

    public void getMessageHandler(Context ctx){
        String messageID = ctx.pathParam("message_id");
        Message message = messageService.getMessage(messageID);
        if(message != null){
            ctx.json(message);
        } else{
            ctx.status(200);
        }
    }

    public void deleteMessageHandler(Context ctx){
        String messageID = ctx.pathParam("message_id");
        Message message = messageService.deleteMessage(messageID);
        if(message != null){
            ctx.json(message);
        } else{
            ctx.status(200);
        }
    }

    public void updateMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        String messageID = ctx.pathParam("message_id");
        Message updatedMessage = messageService.updateMessage(message, messageID);
        if(updatedMessage != null){
            ctx.json(mapper.writeValueAsString(updatedMessage));
        } else {
            ctx.status(400);
        }

    }

    public void getAllMessagesOfUserHandler(Context ctx){
        String accountID = ctx.pathParam("account_id");
        ctx.json(messageService.getUserMessages(accountID));
    }


}