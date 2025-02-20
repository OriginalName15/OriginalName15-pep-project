package Service;

import DAO.SocialMediaDAO;
import Model.Account;

public class SocialMediaService {
    SocialMediaDAO mediaDAO;

    public SocialMediaService(){
        mediaDAO = new SocialMediaDAO();
    }

    public SocialMediaService(SocialMediaDAO mediaDAO){
        this.mediaDAO = mediaDAO;
    }

    public Account userRegistration(Account account){
        return mediaDAO.registerUser(account);
    }
}
