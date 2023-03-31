import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.Arrays;

public class InputHandler {

    public static final String SPLIT_REGEX = " ";
    public static final String AT_LEAST_TWO_RSS = "At least two RSS URL's should be given!";
    public static final Integer URL_MIN_NUM = 2;



    public static void main(String[] args) throws InputException{

        InputHandler inputHandler = new InputHandler();

        // Process input from console
        inputHandler.ProcessInput();


    }

    private String[] ProcessInput() throws InputException
    {
        System.out.print("Enter RSS URL's separated with spaces: ");
        Scanner scanner = new Scanner(System.in);

        // Read array of URL strings input
        String[] url_array = scanner.nextLine().split(SPLIT_REGEX);

        // Check the number of given URL's
        var size = url_array.length;
        if(size < URL_MIN_NUM)
        {
            throw new InputException(AT_LEAST_TWO_RSS);
        }


        return url_array;
    }

}