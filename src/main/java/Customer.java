public class Customer {

    private String name;
    private String surname;
    private String email;
    private CustomerType customerType;
    private final Account account;
    private double companyOverdraftDiscount;

    public Customer(String name, String surname, String email, CustomerType customerType, Account account) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.customerType = customerType;
        this.account = account;
    }

    // use only to create companies
    public Customer(String name, String email, Account account, double companyOverdraftDiscount) {
        this(name, "", email, CustomerType.COMPANY, account);
        this.companyOverdraftDiscount = companyOverdraftDiscount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Account getAccount() {
        return account;
    }

    public double getCompanyOverdraftDiscount() {
        return companyOverdraftDiscount;
    }

    public void setCompanyOverdraftDiscount(double companyOverdraftDiscount) {
        this.companyOverdraftDiscount = companyOverdraftDiscount;
    }

    public void withdraw(double amount, Currency currency) {
        checkCurrencySupport(currency);
        processWithdrawal(amount);
    }

    private void checkCurrencySupport(Currency currency) {
        if (!account.getBalance().getCurrency().equals(currency)) {
            throw new RuntimeException("Currency " + currency + " isn't supported.");
        }
    }

    private void processWithdrawal(double amount) {
        WithdrawalApproach withdrawalApproach = createWithdrawalApproach(account.isPremium());
        withdrawalApproach.withdraw(account, amount);
    }

    private WithdrawalApproach createWithdrawalApproach(boolean isPremium) {
        return isPremium ?
                new PremiumWithdrawalApproach(customerType) :
                new StandartWithdrawalApproach(customerType);
    }

    interface WithdrawalApproach {
        void withdraw(Account account, double amount);
    }

    class StandartWithdrawalApproach implements WithdrawalApproach {

        private final CustomerType customerType;

        public StandartWithdrawalApproach(CustomerType customerType) {
            this.customerType = customerType;
        }

        @Override
        public void withdraw(Account account, double amount) {

            double finalBalance = account.getBalance().getAmount() - amount;

            if (account.getBalance().getAmount() < 0) {
                double overdraft = amount * account.overdraftFee();
                double finalOverdraft = customerType == CustomerType.COMPANY ?
                        overdraft * companyOverdraftDiscount:
                        overdraft;

                account.getBalance().setAmount(finalBalance - finalOverdraft);
            } else {
                account.getBalance().setAmount(finalBalance);
            }
        }
    }

    class PremiumWithdrawalApproach implements WithdrawalApproach {

        private final CustomerType customerType;

        public PremiumWithdrawalApproach(CustomerType customerType) {
            this.customerType = customerType;
        }

        @Override
        public void withdraw(Account account, double amount) {

            double finalBalance = account.getBalance().getAmount() - amount;

            if (account.getBalance().getAmount() < 0) {
                double overdraft = amount * account.overdraftFee();
                double finalOverdraft = customerType == CustomerType.COMPANY ?
                        overdraft * companyOverdraftDiscount / 2 :
                        overdraft;

                account.getBalance().setAmount(finalBalance - finalOverdraft);
            } else {
                account.getBalance().setAmount(finalBalance);
            }
        }
    }
}