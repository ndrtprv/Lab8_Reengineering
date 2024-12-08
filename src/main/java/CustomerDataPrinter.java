public class CustomerDataPrinter {

    public String printCustomerDaysOverdrawn(Customer customer) {
        return customer.getAccount().getCustomerData()
                + customer.getAccount().getDaysOverdrawnData();
    }

    public String printCustomerMoney(Customer customer) {
        return customer.getAccount().getCustomerData()
                + customer.getAccount().getAccountData();
    }

    public String printCustomerAccount(Customer customer) {
        return customer.getAccount().getAccountData();
    }
}