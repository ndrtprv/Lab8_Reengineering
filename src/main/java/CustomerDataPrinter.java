public class CustomerDataPrinter {

    public String printCustomerDaysOverdrawn(Customer customer) {
        String fullName = customer.getName() + " " + customer.getSurname() + " ";
        Account account = customer.getAccount();
        String accountDescription = "Account: IBAN: " + account.getIban() + ", Days Overdrawn: " + account.getDaysOverdrawn();
        return fullName + accountDescription;
    }

    public String printCustomerMoney(Customer customer) {
        String fullName = customer.getName() + " " + customer.getSurname() + " ";
        Account account = customer.getAccount();
        String accountDescription = "";
        accountDescription += "Account: IBAN: " + account.getIban() + ", Money: " + account.getMoney();
        return fullName + accountDescription;
    }

    public String printCustomerAccount(Customer customer) {
        Account account = customer.getAccount();
        return "Account: IBAN: " + account.getIban() + ", Money: "
                + account.getMoney() + ", Account type: " + account.getType();
    }
}