public class Account {

    private String iban;
    private AccountType type;
    private int daysOverdrawn;
    private double money;
    private String currency;
    private Customer customer;

    public Account(AccountType type, int daysOverdrawn) {
        this.type = type;
        this.daysOverdrawn = daysOverdrawn;
    }

    public String getAccountData() {
        return "Account: IBAN: " + iban + ", Money: "
                + money + ", Account type: " + type.getAccountTypeName();
    }

    public String getCustomerData() {
        return customer.getName() + " " + customer.getSurname() + " ";
    }

    public String getDaysOverdrawnData() {
        return "Account: IBAN: " + iban +
                ", Days Overdrawn: " + daysOverdrawn;
    }

    public double bankcharge() {
        double result = 4.5;
        result += overdraftCharge();
        return result;
    }

    private double overdraftCharge() {
        if (type.isPremium()) {
            return daysOverdrawn > 7 ?
                    10 + (daysOverdrawn - 7) * 1.0 :
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

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
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

    public String printCustomer() {
        return customer.getName() + " " + customer.getEmail();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}