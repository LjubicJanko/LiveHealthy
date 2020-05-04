package live.healthy.exception.user;

public class EmailAlreadyExist extends Exception {
    public EmailAlreadyExist() {
        super("Email already exist");
    }
}
