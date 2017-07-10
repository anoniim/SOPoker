package net.solvetheriddle.sopoker.app.login;

import org.junit.Test;

public class LoginPresenterTest {

    @Test(expected = NumberFormatException.class)
    public void test() {
        Integer.valueOf(null);
    }

}