# Comp445-Lab 1

Authors: Swati Pareek (26589596) & Kasthurie Paramasivampillai (40025088)
Submitted on: October 6th 2019 



1) Testing GET command

Input: 
get http://httpbin.org/get?course=networking&assignment=1

Output: 
{
  "args": {
    "assignment": "1", 
    "course": "networking"
  }, 
  "headers": {
    "Host": "10.100.70.151"
  }, 
  "origin": "24.203.243.180, 24.203.243.180", 
  "url": "https://10.100.70.151/get?course=networking&assignment=1"
}

Process finished with exit code 0




2) Testing GET command with verbose option 

Input: 
get -v http://httpbin.org/get?course=networking&assignment=1

Output: 
HTTP/1.1 200 OK
Access-Control-Allow-Credentials: true
Access-Control-Allow-Origin: *
Content-Type: application/json
Date: Sun, 06 Oct 2019 21:31:06 GMT
Referrer-Policy: no-referrer-when-downgrade
Server: nginx
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
Content-Length: 237
Connection: Close

{
  "args": {
    "assignment": "1", 
    "course": "networking"
  }, 
  "headers": {
    "Host": "10.100.70.151"
  }, 
  "origin": "24.203.243.180, 24.203.243.180", 
  "url": "https://10.100.70.151/get?course=networking&assignment=1"
}

Process finished with exit code 0




3) Testing GET command with verbose option and multiple headers

Input: 
get -v -h Host:replacingheaders.org -h header:tester http://httpbin.org/get?course=networking&assignment=1


Output: 
HTTP/1.1 200 OK
Access-Control-Allow-Credentials: true
Access-Control-Allow-Origin: *
Content-Type: application/json
Date: Sun, 06 Oct 2019 21:32:47 GMT
Referrer-Policy: no-referrer-when-downgrade
Server: nginx
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
Content-Length: 276
Connection: Close

{
  "args": {
    "assignment": "1", 
    "course": "networking"
  }, 
  "headers": {
    "Header": "tester", 
    "Host": "replacingheaders.org"
  }, 
  "origin": "24.203.243.180, 24.203.243.180", 
  "url": "https://replacingheaders.org/get?course=networking&assignment=1"
}

Process finished with exit code 0





4) Testing POST command with verbose option, header, and in-line data

Input: 
post -v -h Content-Type:application/json -d {Assignment:1} http://httpbin.org/post

Output: 

HTTP/1.1 200 OK
Access-Control-Allow-Credentials: true
Access-Control-Allow-Origin: *
Content-Type: application/json
Date: Sun, 06 Oct 2019 21:35:35 GMT
Referrer-Policy: no-referrer-when-downgrade
Server: nginx
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
Content-Length: 296
Connection: Close

{
  "args": {}, 
  "data": "{Assignment:1}", 
  "files": {}, 
  "form": {}, 
  "headers": {
    "Content-Length": "14", 
    "Content-Type": "application/json", 
    "Host": "httpbin.org"
  }, 
  "json": null, 
  "origin": "24.203.243.180, 24.203.243.180", 
  "url": "https://httpbin.org/post"
}

Process finished with exit code 0





5) Testing POST command with multiple headers and file for input

Input: 
post -h Content-Type:application/json -h harry:potter -f C:\Users\spare\Documents\git\Comp445-Lab\src\main\java\filedemo.txt http://httpbin.org/post


Output: 
{
  "args": {}, 
  "data": "Assignment:1", 
  "files": {}, 
  "form": {}, 
  "headers": {
    "Content-Length": "12", 
    "Content-Type": "application/json", 
    "Harry": "potter", 
    "Host": "httpbin.org"
  }, 
  "json": null, 
  "origin": "24.203.243.180, 24.203.243.180", 
  "url": "https://httpbin.org/post"
}

Process finished with exit code 0



6) Testing Basic Help

Input: 
help

Output: 
httpc help
httpc is a curl-like application but supports HTTP protocol only.
Usage:  httpc command [arguments]
The commands are: 
    get      executes a HTTP GET request and prints the response.
    post     executes a HTTP POST request and prints the response.
    help     prints this screen.
 Use "httpc help [command]" for more information about a command.

Process finished with exit code 0



7) Testing Basic Help for Post

Input: 
help post

Output: 
httpc help post
Usage:   httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL
Post executes a HTTP POST request for a given URL with inline data or from file.
    -v      prints the detail of the response such as protocol, status, and headers.
    -h key:value       Associates headers to HTTP Request with the format 'key:value'.
    -d string       Associates an inline data to the body HTTP POST request.
    -f file        Associates the content of a file to the body HTTP POST request.
Either [-d] or [-f] can be used but not both.

Process finished with exit code 0



Other notes: 

-Error handling
-Checks for redirections
