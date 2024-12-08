public class CompanyWithdrawalApproach implements WithdrawalApproach{

    private final boolean isPremium;
    private final double overdraftDiscount;

    public CompanyWithdrawalApproach(boolean isPremium, double overdraftDiscount) {
        this.isPremium = isPremium;
        this.overdraftDiscount = overdraftDiscount;
    }

    @Override
    public void withdraw(Account account, double amount) {

        double finalBalance = account.getMoney() - amount;

        if (account.getMoney() < 0) {
            double overdraft = amount * account.overdraftFee();
            double finalOverdraft = isPremium ?
                                    overdraft * overdraftDiscount / 2:
                                    overdraft;

            account.setMoney(finalBalance - finalOverdraft);
        } else {
            account.setMoney(finalBalance);
        }
    }
}
