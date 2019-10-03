import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class HTTPRequest {
    private String[] args;

    public HTTPRequest(String[] args){
        this.args = args;

    }

    public void HandlingRequest(){

        if (args[0].equalsIgnoreCase("help")){
            if (args.length==1){
                System.out.println("httpc help");
                System.out.println("httpc is a curl-like application but supports HTTP protocol only.");
                System.out.println("Usage:  httpc command [arguments]");
                System.out.println("The commands are: ");
                System.out.println("    get      executes a HTTP GET request and prints the response.");
                System.out.println("    post     executes a HTTP POST request and prints the response.");
                System.out.println("    help     prints this screen.");
                System.out.println(" Use \"httpc help [command]\" for more information about a command.");
            }
            else if (args[1].equalsIgnoreCase("get")){
                System.out.println("httpc help get");
                System.out.println("Usage:   httpc get [-v] [-h key:value] URL");
                System.out.println("Get executes a HTTP GET request for a given URL.");
                System.out.println("    -v      prints the detail of the response such as protocol, status, and headers.");
                System.out.println("    -h key:value       Associates headers to HTTP Request with the format 'key:value'.");
            }
            else if (args[1].equalsIgnoreCase("post")){
                System.out.println("httpc help post");
                System.out.println("Usage:   httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL");
                System.out.println("Post executes a HTTP POST request for a given URL with inline data or from file.");
                System.out.println("    -v      prints the detail of the response such as protocol, status, and headers.");
                System.out.println("    -h key:value       Associates headers to HTTP Request with the format 'key:value'.");
                System.out.println("    -d string       Associates an inline data to the body HTTP POST request.");
                System.out.println("    -f file        Associates the content of a file to the body HTTP POST request.");
                System.out.println("Either [-d] or [-f] can be used but not both.");
            }

            else{
                System.err.println("httpc help " + args[1] + " is not a valid command.");
                System.err.println(" Use \"httpc help\" for more information about the list of valid commands.");
            }

        }
        else if (args[0].equals("post")){
            POSTRequest();
            //execute command

        }
        else if (args[0].equals("get")){

            //execute command

        }
        else{
            System.err.println("httpc " + args[0] + " is not a valid command.");
            System.err.println(" Use \"httpc help\" for more information about the list of valid commands.");
        }
    }
    //Parsing

    public String Headers(){
        OptionParser parser = new OptionParser();
        parser.accepts("h")
                .withRequiredArg()
                .ofType(String.class);
        parser.allowsUnrecognizedOptions();
        OptionSet ResultingHeaders = parser.parse(args);

        //get a list of all the headers
        List<String> headerlist = (List<String>)ResultingHeaders.valuesOf("h");

        // Create a string called Headers and add all the headers from the list
        String Headers="";
        for(String header: headerlist){
            Headers += header.replaceAll(":", ": ") + "\r\n";
        }
        return Headers;
    }


    public boolean Verbose() {
        OptionParser parser = new OptionParser();
        parser.accepts("v");
        parser.allowsUnrecognizedOptions();
        OptionSet Verbose = parser.parse(args);
        return Verbose.has("v");
    }

    public String Data() {
        OptionParser parser = new OptionParser();
        parser.accepts("d")
                .withRequiredArg()
                .ofType(String.class);
        parser.allowsUnrecognizedOptions();
        OptionSet Data = parser.parse(args);
        return (String)Data.valueOf("d");
    }

    public String FileData() {
        OptionParser parser = new OptionParser();
        parser.accepts("f")
                .withRequiredArg()
                .ofType(String.class);
        parser.allowsUnrecognizedOptions();
        OptionSet ResultingFile = parser.parse(args);
        String FileName= (String)ResultingFile.valueOf("f");
        String data = "";

        try{

            //using the file name, the buffer reader will read the file
           File dFile = new File(FileName);
            BufferedReader bf = new BufferedReader(new FileReader(dFile));
            String line;
            while ((line=bf.readLine())!=null){
               data += line;
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return data;
    }

    //Post Request
    private void POSTRequest(){





    }

}
