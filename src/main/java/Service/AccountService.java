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
}
