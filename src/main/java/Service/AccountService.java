package Service;
import DAO.AccountDAO;
import Model.Account;
import java.util.List;
public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDao){
        this.accountDAO = accountDao;
    }

    public List<Account> getAllAccounts(){
        return this.accountDAO.getAllAccounts();
    }

    public Account addAccount(Account account){
        return this.accountDAO.addAccount(account);
    }

    public boolean exists (Account account){
        return this.accountDAO.usernameExists(account);
    }

    public boolean exists (Integer ID){
        Account account = this.accountDAO.getAccount(ID);
        return this.accountDAO.usernameExists(account);
    }

    public Account findAccount(Account account){
        return this.accountDAO.getAccount(account);
    }

}
