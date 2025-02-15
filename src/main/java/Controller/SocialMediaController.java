package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import DAO.AccountDAO;
import Service.AccountService;
import Model.Message;
import DAO.MessageDAO;
import Service.MessageService;
import java.util.List;
 
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

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
        app.post("/register", this::registerHandler);
        app.post("/login",this::loginHandler);
        app.post("/messages",this::messageHandler);
        app.get("/messages",this::allMessagesHandler);
        app.get("/messages/{message_id}",this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    
    private void registerHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
            context.status(200);
        }
        else{
            context.status(400);
        }
    }
    private void loginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        if(accountService.exists(account)){
            Account existingAccount = accountService.findAccount(account);
            if(existingAccount!=null){
                context.json(mapper.writeValueAsString(existingAccount));
                context.status(200);
            }
            else{
                context.status(401);
            }
        }
        else{
            context.status(401);
        }
        
    }

    private void messageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage == null){
            context.status(400);
        }
        else{
            context.json(mapper.writeValueAsString(addedMessage));
            context.status(200);
        }
    }

    private void allMessagesHandler(Context context) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
        context.status(200);
    }

    private void getMessageHandler(Context context) throws JsonProcessingException{
        String ID = context.pathParam("message_id");
        Message foundMessage = messageService.findMessage(ID);
        if(foundMessage!=null){
            context.json(foundMessage);
        }
        context.status(200);
    }

    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        String ID = context.pathParam("message_id");
        Message deletedMessage = messageService.deleteMessage(ID);
        if(deletedMessage!=null){
            context.json(deletedMessage);
        }
        context.status(200);
    }

    private void updateMessageHandler(Context context) throws JsonProcessingException{
        String ID = context.pathParam("message_id");
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message updatedMessage = messageService.updateMessage(ID, message.getMessage_text());
        if(updatedMessage!=null && updatedMessage.getMessage_text()!=""){
            context.json(updatedMessage);
            context.status(200);
        }
        else{
            context.status(400);
        }
    }


    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessagesWithID(Integer.parseInt(context.pathParam("account_id")));
        context.json(messages);
        context.status(200);
    }



}