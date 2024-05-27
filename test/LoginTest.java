import controller.Controller;

public class LoginTest {

    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        controller.login("login", "aca", "1");
        System.out.println(controller.getUser());
    }

}
