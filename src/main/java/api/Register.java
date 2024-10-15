package api;

public class Register extends AuthBase {
    public Register(String email, String password) {
        super(email, password);
    }
}