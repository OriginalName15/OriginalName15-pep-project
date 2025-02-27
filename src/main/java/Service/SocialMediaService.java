package Service;

import DAO.SocialMediaDAO;
import Model.Account;
import Model.Message;
import java.util.List;

public class SocialMediaService {
    SocialMediaDAO mediaDAO;

    public SocialMediaService() {
        mediaDAO = new SocialMediaDAO();
    }

    public SocialMediaService(SocialMediaDAO mediaDAO) {
        this.mediaDAO = mediaDAO;
    }

    // Check if username already exists
    public boolean isUsernameTaken(String username) {
        return mediaDAO.isUsernameTaken(username);
    }

    // Register new user
    public Account userRegistration(Account account) {
        return mediaDAO.registerUser(account);
    }

    //login user
    public boolean loginUser(String userName, String password){
        return mediaDAO.loginUser(userName, password);
    }

    // get account from username
    public Account getAccountByUsername(String username) {
        return mediaDAO.getAccountByUsername(username);
    }


    //get all messages
    public List<Message> getAllMessages(){
        return mediaDAO.getAllMessages();
    }

    //get message from ID
    public Message getMessageByID(Integer message_id){
        return mediaDAO.getMessageByID(message_id);
    }

    //get all messages from userID
    public List<Message> getAllMessagesByUserID(Integer user_ID){
        return mediaDAO.getAllMessagesByUserID(user_ID);
    }
}
