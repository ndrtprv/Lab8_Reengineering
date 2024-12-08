public class PersonWithdrawalApproach implements WithdrawalApproach{

    @Override
    public void withdraw(Account account, double amount) {

        double finalBalance = account.getMoney() - amount;

        if (account.getMoney() < 0) {
            double overdraft = amount * account.overdraftFee();
            account.setMoney(finalBalance - overdraft);
        } else {
            account.setMoney(finalBalance);
        }
    }
}
