public class Account {

    private Money balance;
    private String iban;
    private boolean isPremium;
    private int daysOverdrawn;
    private Customer customer;

    public Account(boolean isPremium, int daysOverdrawn) {
        this.isPremium = isPremium;
        this.daysOverdrawn = daysOverdrawn;
    }

    public String getAccountData() {
        return "Account: IBAN: " + getIban() + ", Money: "
                + getBalance().getAmount() + ", Account type: " + (isPremium ? "premium" : "normal");
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
        if (isPremium()) {
            return getDaysOverdrawn() > 7 ?
                    10 + (getDaysOverdrawn() - 7) * 1.0 :
                    10;
        } else {
            return getDaysOverdrawn() * 1.75;
        }
    }

    public double overdraftFee() {
        return isPremium() ? 0.10 : 0.20;
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

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public void setDaysOverdrawn(int daysOverdrawn) {
        this.daysOverdrawn = daysOverdrawn;
    }
}