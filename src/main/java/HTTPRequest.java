import joptsimple.OptionParser;
import joptsimple.OptionSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;


public class HTTPRequest {
    private String[] args;

    public HTTPRequest(String[] args){
        this.args = args;

    }

    public void HandlingRequest() throws MalformedURLException{

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
            String inputURL = args[args.length-1];
            boolean isVerbose =  parseForVerbose();
           Map<String, String> headersMap = parseForHeaders();
           String data = parseForData();
            POSTRequest(inputURL, isVerbose, headersMap, parseForData());
            //execute command

        }
        else if (args[0].equals("get")){
            String inputURL = args[args.length-1];
            boolean isVerbose =  parseForVerbose();
            Map<String, String> headersMap = parseForHeaders();


            if(inputURL.startsWith("https://")|| inputURL.startsWith("http://"))
            GETRequest(inputURL, isVerbose, headersMap);

            else
                throw new MalformedURLException("The Following URL is not valid :" + inputURL);
        }
        else{
            System.err.println("httpc " + args[0] + " is not a valid command.");
            System.err.println(" Use \"httpc help\" for more information about the list of valid commands.");
        }
    }

    //Parsing

    private Map<String, String> parseForHeaders(){
        OptionParser parser = new OptionParser();
        parser.accepts("h")
                .withRequiredArg()
                .ofType(String.class);
        parser.allowsUnrecognizedOptions();
        OptionSet ResultingHeaders = parser.parse(args);

        //get a map of all the headers
        List<String> headerList = (List<String>)ResultingHeaders.valuesOf("h");
        Map<String, String>  headerMap = new HashMap<String, String>() ;

        // Create a string called headers and add all the headers from the list
        for(String header: headerList){
            String key = header.substring(0,header.indexOf(":"));
            String value = header.substring(header.indexOf(":") + 1);
            headerMap.put(key,value);
        }
        return headerMap;
    }


    private boolean parseForVerbose() {
        OptionParser parser = new OptionParser();
        parser.accepts("v");
        parser.allowsUnrecognizedOptions();
        OptionSet Verbose = parser.parse(args);
        return Verbose.has("v");
    }

    private String parseForData() {
        OptionParser parser = new OptionParser();
        parser.accepts("d")
                .withRequiredArg()
                .ofType(String.class);
        parser.allowsUnrecognizedOptions();
        OptionSet Data = parser.parse(args);
        String data =  (String)Data.valueOf("d");
        if (data.length() < 0) return "";
        return (data);
    }

    private String parseForFileData() {
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
            System.err.println(e.getMessage());
        }
        return data;
    }

    //Post Request

    private void POSTRequest(String str, boolean isVerbose,  Map <String, String> headersMap, String data){


    try {

        String host;
        int port;


        String url = str.replaceAll("'", "");
        URL u = new URL(url);
        host = u.getHost();
        port = u.getDefaultPort();

        InetAddress addr = InetAddress.getByName(host);
        Socket socket = new Socket(addr, port);

        String requestHeaders = "";
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            requestHeaders+= (entry.getKey() + ":" + entry.getValue() +"\r\n");
        }

        // Send Request
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
        wr.write("POST "+ u +" HTTP/1.0\r\n");
        if(requestHeaders.length()>0)
            wr.write(requestHeaders);

        if(parseForFileData().equals("") && data.length() < 0) {
            wr.write("\r\n");
        }

        // Send body, write data or file if they are there

        if (!parseForFileData().equals("")){
            wr.write(parseForFileData());
        }

        else if(data.length() > 0){
            wr.write("Content-Length:" + data.length() + "\r\n");
             wr.write("\r\n");
            wr. write(data + "\r\n");
        }
        wr.flush();

        BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;

        boolean isResponse = false;

        while ((line = rd.readLine()) != null) {
            if (isVerbose || isResponse)
                System.out.println(line);

            else {
                line = line.trim();
                if(line.equals("{") || line.equals("}")) {
                    System.out.println(line);
                    isResponse = !isResponse;
                }

            }
        }

        wr.close();
        rd.close();

    }
    catch (Exception e) {
        e.printStackTrace();

    }

            }


    //Get Request
    private void GETRequest(String inputURLString, boolean isVerbose, Map <String, String> headersMap) throws MalformedURLException{
        URL url = new URL(inputURLString);  //throws malformed url
        String host = url.getHost();
        int  port = url.getDefaultPort();
        String contentForGet = inputURLString.substring(inputURLString.indexOf(host) + host.length());
        String requestHeaders = "";
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            requestHeaders+= (entry.getKey() + ":" + entry.getValue() +"\r\n");
        }

        try {
            Socket socket = new Socket(host, port);


            // Send request
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
            wr.write("GET "+ contentForGet +" HTTP/1.0\r\n");
            //Request headers
            if(requestHeaders.length()>0)
            wr.write(requestHeaders);
            wr.write("\r\n");
            wr.flush();


            BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            boolean isResponse = false;

            while ((line = rd.readLine()) != null) {
                if (isVerbose || isResponse)
                System.out.println(line);

                else {
                    line = line.trim();
                    if(line.equals("{") || line.equals("}")) {
                        System.out.println(line);
                        isResponse = !isResponse;
                    }
                }
            }

            wr.close();
            rd.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
