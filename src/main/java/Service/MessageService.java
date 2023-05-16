package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private AccountDAO accountDAO;
    private MessageDAO messageDAO;
    private int maxChar = 255;
    private int minChar = 0;

    public MessageService(){
        accountDAO = new AccountDAO();
        messageDAO = new MessageDAO();
    }

    public Message sendMessage(Message message){
        if(accountDAO.checkUserExists(message.message_id)){
            return null;
        } else if (message.message_text.length() <= minChar || message.message_text.length() >= maxChar ){
            return null;
        } else {
            return messageDAO.createMessage(message);
        }
        
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
    
    public Message getMessage(String messageID){
        return messageDAO.getMessage(messageID);
    }

    public Message deleteMessage(String messageID){
        return messageDAO.deleteMessage(messageID);
    }

    public Message updateMessage(Message message, String messageID){
        if(this.getMessage(messageID) == null){
            return null;
        } else if(message.message_text.length() <= minChar || message.message_text.length() >= maxChar ){
            return null;
        } else return messageDAO.updateMessage(message, messageID);
    }
    public List<Message> getUserMessages(String accountID){
        return messageDAO.getUserMessages(accountID);
    }
    
}
