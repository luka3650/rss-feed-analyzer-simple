// IllegalArgumentException for wrong URL number argument
public class InputException extends IllegalArgumentException {
    public InputException(){}

    public InputException(String message)
    {
        // Call Exception class constructor with already implemented logic
        super(message);
    }

}