package Model;

public class MyException extends Exception {
    private final String message;

    public MyException(String _msg) {
        message = _msg;
    }

    public String toString() {
        return message;
    }
}
