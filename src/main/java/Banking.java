public class Banking {

    public void withdraw(Customer customer, double amount, String currency) {

        Account account = customer.getAccount();

        if (!account.getCurrency().equals(currency)) {
            throw new RuntimeException("Currency " + currency + " isn't supported.");
        }

        processWithdrawal(customer, amount, account.getType().isPremium());
    }

    private void processWithdrawal(Customer customer, double amount, boolean isPremium) {

        switch (customer.getCustomerType()) {
            case COMPANY:
                processWithdrawalForCompany(customer, amount, isPremium);
                break;
            case PERSON:
                processWithdrawalForPerson(customer.getAccount(), amount);
                break;
        }
    }

    private void processWithdrawalForCompany(Customer customer, double amount, boolean isPremium) {

        Account account = customer.getAccount();
        double overdraftDiscount = customer.getCompanyOverdraftDiscount();
        double finalBalance = account.getMoney() - amount;

        if (isAccountOverdrawn(account)) {
            double overdraft = getOverdraftFee(amount, account, overdraftDiscount, isPremium);
            account.setMoney(finalBalance - overdraft);
        } else {
            account.setMoney(finalBalance);
        }
    }

    private double getOverdraftFee(double amount, Account account, double overdraftDiscount, boolean isPremium) {
        double overdraftFee = amount * account.overdraftFee() * overdraftDiscount;
        return isPremium ? overdraftFee / 2 : overdraftFee;
    }

    private boolean isAccountOverdrawn(Account account) {
        return account.getMoney() < 0;
    }

    private void processWithdrawalForPerson(Account account, double amount) {

        double finalBalance = account.getMoney() - amount;

        if (isAccountOverdrawn(account)) {
            double overdraft = getOverdraftFee(amount, account, 1, false);
            account.setMoney(finalBalance - overdraft);
        } else {
            account.setMoney(finalBalance);
        }
    }
}