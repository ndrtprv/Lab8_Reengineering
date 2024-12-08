import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CustomerTest {

    @Test
    public void testWithdrawPersonWithNormalAccount() throws Exception {
        Account account = getAccountByTypeAndMoney(false, 34.0);
        Customer customer = getPersonCustomer(account);
        customer.withdraw(10, Currency.EUR);
        assertThat(account.getBalance().getAmount(), is(24.0));
    }

    @Test
    public void testWithdrawPersonWithNormalAccountAndOverdraft() throws Exception {
        Account account = getAccountByTypeAndMoney(false, -10.0);
        Customer customer = getPersonCustomer(account);
        customer.withdraw(10, Currency.EUR);
        assertThat(account.getBalance().getAmount(), is(-22.0));
    }

    @Test
    public void testWithdrawPersonWithPremiumAccount() throws Exception {
        Account account = getAccountByTypeAndMoney(true, 34.0);
        Customer customer = getPersonCustomer(account);
        customer.withdraw(10, Currency.EUR);
        assertThat(account.getBalance().getAmount(), is(24.0));
    }

    @Test
    public void testWithdrawPersonWithPremiumAccountAndOverdraft() throws Exception {
        Account account = getAccountByTypeAndMoney(true, -10.0);
        Customer customer = getPersonCustomer(account);
        customer.withdraw(10, Currency.EUR);
        assertThat(account.getBalance().getAmount(), is(-21.0));
    }

    @Test
    public void testWithdrawCompanyWithNormalAccount() throws Exception {
        Account account = getAccountByTypeAndMoney(false, 34);
        Customer customer = getCompanyCustomer(account);
        customer.withdraw(10, Currency.EUR);
        assertThat(account.getBalance().getAmount(), is(24.0));
    }

    @Test
    public void testWithdrawCompanyWithNormalAccountAndOverdraft() throws Exception {
        Account account = getAccountByTypeAndMoney(false, -10);
        Customer customer = getCompanyCustomer(account);
        customer.withdraw(10, Currency.EUR);
        assertThat(account.getBalance().getAmount(), is(-22.0));
    }

    @Test
    public void testWithdrawCompanyWithPremiumAccount() throws Exception {
        Account account = getAccountByTypeAndMoney(true, 34);
        Customer customer = getCompanyCustomer(account);
        customer.withdraw(10, Currency.EUR);
        assertThat(account.getBalance().getAmount(), is(24.0));
    }

    @Test
    public void testWithdrawCompanyWithPremiumAccountAndOverdraft() throws Exception {
        Account account = getAccountByTypeAndMoney(true, -10);
        Customer customer = getCompanyCustomer(account);
        customer.withdraw(10, Currency.EUR);
        assertThat(account.getBalance().getAmount(), is(-20.25));
    }

    @Test
    public void testPrintCustomerDaysOverdrawn() throws Exception {
        Customer customer = getPersonWithAccount(false);
        CustomerDataPrinter customerDataPrinter = new CustomerDataPrinter();
        assertThat(customerDataPrinter.printCustomerDaysOverdrawn(customer),
                is("danix dan Account: IBAN: RO023INGB434321431241, Days Overdrawn: 9"));
    }

    @Test
    public void testPrintCustomerMoney() throws Exception {
        Customer customer = getPersonWithAccount(false);
        CustomerDataPrinter customerDataPrinter = new CustomerDataPrinter();
        assertThat(customerDataPrinter.printCustomerMoney(customer),
                is("danix dan Account: IBAN: RO023INGB434321431241, Money: 34.0"));
    }

    @Test
    public void testPrintCustomerAccountNormal() throws Exception {
        Customer customer = getPersonWithAccount(false);
        CustomerDataPrinter customerDataPrinter = new CustomerDataPrinter();
        assertThat(customerDataPrinter.printCustomerAccount(customer),
                is("Account: IBAN: RO023INGB434321431241, Money: 34.0, Account type: normal"));
    }

    @Test
    public void testPrintCustomerAccountPremium() throws Exception {
        Customer customer = getPersonWithAccount(true);
        CustomerDataPrinter customerDataPrinter = new CustomerDataPrinter();
        assertThat(customerDataPrinter.printCustomerAccount(customer),
                is("Account: IBAN: RO023INGB434321431241, Money: 34.0, Account type: premium"));
    }

    private Customer getPersonWithAccount(boolean premium) {
        AccountType accountType = new AccountType(premium);
        Account account = new Account(accountType, 9);
        Customer customer = getPersonCustomer(account);
        account.setIban("RO023INGB434321431241");
        account.getBalance().setAmount(34.0);
        account.getBalance().setCurrency(Currency.EUR);
        return customer;
    }

    private Account getAccountByTypeAndMoney(boolean premium, double money) {
        AccountType accountType = new AccountType(premium);
        Account account = new Account(accountType, 9);
        account.setIban("RO023INGB434321431241");
        account.getBalance().setAmount(money);
        account.getBalance().setCurrency(Currency.EUR);
        return account;
    }

    private Customer getPersonCustomer(Account account) {
        Customer customer = new Customer("danix", "dan", "dan@mail.com", CustomerType.PERSON, account);
        account.setCustomer(customer);
        return customer;
    }

    private Customer getCompanyCustomer(Account account) {
        Customer customer = new Customer("company", "company@mail.com", account, 0.50);
        account.setCustomer(customer);
        return customer;
    }
}