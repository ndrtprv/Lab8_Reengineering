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
        this.companyOverdraftDiscount = 1;
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

    public void withdraw(double amount, String currency) {

        checkCurrencySupport(currency);
        processWithdrawal(amount);
    }

    private void checkCurrencySupport(String currency) {
        if (!account.getCurrency().equals(currency)) {
            throw new RuntimeException("Currency " + currency + " isn't supported.");
        }
    }

    private void processWithdrawal(double amount) {

        WithdrawalApproach withdrawalApproach = createWithdrawalApproach(account.getType().isPremium());
        withdrawalApproach.withdraw(account, amount);
    }

    private WithdrawalApproach createWithdrawalApproach(boolean isPremium) {
        return isPremium ?
                new StandartWithdrawalApproach(customerType) :
                new PremiumWithdrawalApproach(customerType);
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

            double finalBalance = account.getMoney() - amount;

            if (account.getMoney() < 0) {
                double overdraft = amount * account.overdraftFee();
                double finalOverdraft = customerType == CustomerType.COMPANY ?
                        overdraft * companyOverdraftDiscount:
                        overdraft;

                account.setMoney(finalBalance - finalOverdraft);
            } else {
                account.setMoney(finalBalance);
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

            double finalBalance = account.getMoney() - amount;

            if (account.getMoney() < 0) {
                double overdraft = amount * account.overdraftFee();
                double finalOverdraft = customerType == CustomerType.COMPANY ?
                        overdraft * companyOverdraftDiscount / 2 :
                        overdraft;

                account.setMoney(finalBalance - finalOverdraft);
            } else {
                account.setMoney(finalBalance);
            }
        }
    }
}