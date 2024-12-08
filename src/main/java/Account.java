public class Account {

    private Money balance;
    private String iban;
    private AccountType type;
    private int daysOverdrawn;
    private String currency;
    private Customer customer;

    public Account(AccountType type, int daysOverdrawn) {
        this.type = type;
        this.daysOverdrawn = daysOverdrawn;
    }

    public String getAccountData() {
        return "Account: IBAN: " + getIban() + ", Money: "
                + getBalance().getAmount() + ", Account type: " + getType().getAccountTypeName();
    }

    public String getMoneyData() {
        return "Account: IBAN: " + getIban() + ", Money: " + getBalance().getAmount();
    }

    public String getDaysOverdrawnData() {
        return "Account: IBAN: " + getIban() +
                ", Days Overdrawn: " + getDaysOverdrawn();
    }

    public double bankcharge() {
        double result = 4.5;
        result += overdraftCharge();
        return result;
    }

    private double overdraftCharge() {
        if (type.isPremium()) {
            return getDaysOverdrawn() > 7 ?
                    10 + (getDaysOverdrawn() - 7) * 1.0 :
                    10;
        } else {
            return getDaysOverdrawn() * 1.75;
        }
    }

    public double overdraftFee() {
        return type.isPremium() ? 0.10 : 0.20;
    }

    public int getDaysOverdrawn() {
        return daysOverdrawn;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AccountType getType() {
        return type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}