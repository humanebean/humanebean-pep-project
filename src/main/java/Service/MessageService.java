package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;
import Service.AccountService;
public class MessageService {
    private MessageDAO messageDAO;
    private AccountService accountService;

    public MessageService(){
        messageDAO = new MessageDAO();
        this.accountService = new AccountService();
    }

    public List<Message> getAllMessages(){
        return this.messageDAO.getAllMessages();
    }
    public Message addMessage(Message message){

        if(message.getMessage_text()=="" || message.getMessage_text().length()>=255 || !accountService.exists(message.posted_by)){
            return null;
        }
        return messageDAO.addMessage(message);
    }

    public Message findMessage(Message message){
        return this.messageDAO.getMessage(message);
    }

    public Message findMessage(String ID){
        return this.messageDAO.getMessage(Integer.parseInt(ID));
    }

    public Message deleteMessage(String ID){
        return this.messageDAO.deleteMessage(Integer.parseInt(ID));
    }

    public Message updateMessage(String ID, String messageBody){
        return this.messageDAO.updateMessage(Integer.parseInt(ID),messageBody);
    }

    public List<Message> getAllMessagesWithID(Integer ID){
        return this.messageDAO.getAllMessagesWithID(ID);
    }

}
