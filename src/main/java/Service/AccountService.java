package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        if (account.username.length() <= 0){
            return null;
        } else if (account.password.length() < 4){
            return null;
        } else if (accountDAO.checkUserExists(account)){
            return null;
        }
        
        else {
            return accountDAO.insertAccount(account);
        }
        
    }

    public Account loginAccount(Account account){
        Account verifiedAccount = accountDAO.checkUserVerified(account);
        if(verifiedAccount != null){
            return verifiedAccount;
        } else return null;
    }
    
}
