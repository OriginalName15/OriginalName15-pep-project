package Controller;

import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    SocialMediaService socialMediaService;
    public SocialMediaController(){
        socialMediaService = new SocialMediaService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUser);
        app.post("/login", this::loginUser);
        app.post("/messages", this::newMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageByID);
        app.delete("/messages/{message_id}", this::deleteMessageByID);
        app.patch("/messages/{message_id}", this::updateMessageByID);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     *
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
        */

    //registers user
    //check if username is not blank and password is at least 4 characters
    //if successfull status 200
    //if not status 400
    private void registerUser(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
    
        // Check if the username is blank or password is too short
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            ctx.status(400);
            return;
        }

        // Check if the username already exists
        if (socialMediaService.isUsernameTaken(account.getUsername())) {
            ctx.status(400);
            return;
        }

        // Register the user and return the account object with account_id
        Account registeredAccount = socialMediaService.userRegistration(account);

        if (registeredAccount != null) {
            ctx.status(200).json(registeredAccount);
        } else {
            ctx.status(400);
        }


    }

    //login user
    //if successfull status 200
    //if not status 401
    private void loginUser(Context ctx)throws JsonProcessingException{


    }

    //creates new message
    //check if message is not blank and not over 255 characters
    //if successfull status 200
    //if not status 400
    private void newMessage(Context ctx){

    }

    //gets all messages
    //should always be status 200
    private void getAllMessages(Context ctx){
        ctx.status(200).json(socialMediaService.getAllMessages());
    }

    //contain message indentified by id
    // should always be status 200
    private void getMessageByID(Context ctx){
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = socialMediaService.getMessageByID(messageId);
 
        if (message == null) {
            ctx.status(200);
        } else {
            ctx.status(200).json(message);
        }
    }

    //delete message by id
    //status should be 200
    private void deleteMessageByID(Context ctx){

    }

    //update a message by id
    //if successfull status 200
    //if not status 400
    private void updateMessageByID(Context ctx){

    }

    //should get all messages by a user
    //status is always 200
    private void getAllMessagesByUser(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = socialMediaService.getAllMessagesByUserID(userId);
        ctx.status(200).json(messages);
    }


}