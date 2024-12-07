public class Banking {

    public void withdraw(Customer customer, double sum, String currency) {
        Account account = customer.getAccount();

        if (!account.getCurrency().equals(currency)) {
            throw new RuntimeException("Can't extract withdraw " + currency);
        }
        setMoneyForCustomer(customer, sum, account.getType().isPremium());
    }

    private void setMoneyForCustomer(Customer customer, double sum, boolean isPremium) {
        switch (customer.getCustomerType()) {
            case COMPANY:
                setMoneyForCompany(customer, sum, isPremium);
                break;
            case PERSON:
                setMoneyForPerson(customer.getAccount(), sum);
                break;
        }
    }

    private void setMoneyForCompany(Customer customer, double sum, boolean isPremium) {
        Account account = customer.getAccount();
        double overdraftDiscount = customer.getCompanyOverdraftDiscount();
        double finalSum = account.getMoney() - sum;
        // we are in overdraft
        if (account.getMoney() < 0) {
            double overdraft = sum * account.overdraftFee() * overdraftDiscount;
            account.setMoney(finalSum - (isPremium ? overdraft / 2 : overdraft));
        } else {
            account.setMoney(finalSum);
        }
    }

    private void setMoneyForPerson(Account account, double sum) {
        // we are in overdraft
        if (account.getMoney() < 0) {
            account.setMoney((account.getMoney() - sum) - sum * account.overdraftFee());
        } else {
            account.setMoney(account.getMoney() - sum);
        }
    }
}