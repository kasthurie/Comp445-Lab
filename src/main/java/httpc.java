
import java.net.MalformedURLException;


public class httpc{

    public static void main(String[] args){

        HTTPRequest httpRequest = new HTTPRequest(args);
        httpRequest.HandlingRequest();

}

        try {
            httpRequest.HandlingRequest();
        }

        catch (MalformedURLException e) {
            System.err.println(e.getMessage());
            System.err.println("Please enter valid URL");
        }
    }


}
