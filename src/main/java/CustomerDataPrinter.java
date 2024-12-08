public class CustomerDataPrinter {

    public String printCustomerDaysOverdrawn(Customer customer) {
        return customer.getName() + " " + customer.getSurname()
                + " " + customer.getAccount().getDaysOverdrawnData();
    }

    public String printCustomerMoney(Customer customer) {
        return customer.getName() + " " + customer.getSurname()
                + " " + customer.getAccount().getMoneyData();
    }

    public String printCustomerAccount(Customer customer) {
        return customer.getAccount().getAccountData();
    }
}