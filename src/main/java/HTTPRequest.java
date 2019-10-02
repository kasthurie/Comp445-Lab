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

        if (args[0].equals("help")){
            if (args.length==1){
                //print help message
            }
            else if (args[1].equals("get")){
                //print help get message
            }
            else if (args[1].equals("post")){
                //print help post message
            }

            else{
                //print error message
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
            //print error message
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
