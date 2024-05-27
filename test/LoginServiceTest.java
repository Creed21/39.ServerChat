import model.User;
import service.LoginService;

public class LoginServiceTest {

    private LoginService loginService;

    public LoginServiceTest() {
        loginService = new LoginService();
    }

    public void testLogin() {
        String username = "aca";
        String password = "1";

        User user = loginService.login(username, password);

        if(user != null)
            System.out.println("User: " + user);
        else
            System.out.println("something went wrong, check your code / credentials");
    }

    public static void main(String[] args) {
        LoginServiceTest test = new LoginServiceTest();

        test.testLogin();
    }

}
