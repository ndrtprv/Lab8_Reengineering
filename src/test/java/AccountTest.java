import org.junit.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AccountTest {

    @Test
    public void testBankchargePremiumLessThanAWeek() throws Exception {
        Account account = getPremiumAccount(5);
        assertThat(account.bankcharge(), is(14.5));
    }

    @Test
    public void testBankchargePremiumMoreThanAWeek() throws Exception {
        Account account = getPremiumAccount(9);
        assertThat(account.bankcharge(), is(16.5));
    }

    @Test
    public void testOverdraftFeePremium() throws Exception {
        Account account = getPremiumAccount(9);
        assertThat(account.overdraftFee(), is(0.10));
    }

    @Test
    public void testOverdraftFeeNotPremium() throws Exception {
        Account account = getNormalAccount();
        assertThat(account.overdraftFee(), is(0.20));
    }

    private Account getNormalAccount() {
        return new Account(false, 9);
    }

    private Account getPremiumAccount(int daysOverdrawn) {
        return new Account(true, daysOverdrawn);
    }
}