public class Banking {

    public void withdraw(Customer customer, double amount, String currency) {

        Account account = customer.getAccount();

        checkCurrencySupport(currency, account);

        processWithdrawal(customer, amount, account.getType().isPremium());
    }

    private void checkCurrencySupport(String currency, Account account) {
        if (!account.getCurrency().equals(currency)) {
            throw new RuntimeException("Currency " + currency + " isn't supported.");
        }
    }

    private void processWithdrawal(Customer customer, double amount, boolean isPremium) {

        WithdrawalApproach withdrawalApproach = createWithdrawalApproach(customer, isPremium);
        withdrawalApproach.withdraw(customer.getAccount(), amount);
    }

    private WithdrawalApproach createWithdrawalApproach(Customer customer, boolean isPremium) {
        return customer.getCustomerType() == CustomerType.COMPANY ?
                new CompanyWithdrawalApproach(isPremium, customer.getCompanyOverdraftDiscount()) :
                new PersonWithdrawalApproach();
    }
}