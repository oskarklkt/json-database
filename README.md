# oklekot-JSON_Database 🗄️

---

## Quick start 🏎️

<details><summary>Click to expand</summary>

### Server
1. Run ServerSideApp!
### Client
2. Add command line arguments in ClientSideApp class (containing main() method) in configurations settings of current class and then run it.


When inserting commands:
```
-t {type} -k{key} -v{value}
```
When getting commands from .json file:
```
-in {file name}       
```

It is also possible to put/get nested JSON objects like this:
```
Sent: {"type":"get","key":["person","name"]}
Received: {"response":"OK","value":"Elon Musk"}
```
_MORE EXAMPLES UNDER STAGE 6 OVERVIEW_

##### Where: 
1. -t {type} is mandatory, possible types: set, get, delete and should be a String value
2. -k {key} is also mandatory, any String
3. -v {value} is only mandatory in set command 
##### Examples: 
Here is what the set request format should look like:
```
{ "type": "set", "key": "Secret key", "value": "Secret value" }
```
The responses should be in the JSON format. Please consider the examples below.
```
{ "response": "OK" }
```
The get request
```
{ "type": "get", "key": "Secret key" }
```
The delete request
```
{ "type": "delete", "key": "Key that doesn't exist" }
```
In the case of a get request with a key stored in the database:
```
{ "response": "OK", "value": "Secret value" }
```
In the case of a get or delete request with a key that doesn't exist:
```
{ "response": "ERROR", "reason": "No such key" }
```
</details>

## Project's stages overview👀:
<details><summary>Click to expand</summary>

### Hyperskill project: JSON_Database
more information: https://hyperskill.org/projects/65?track=17
### Stage 1️⃣ Create a database
##### Description
JSON database is a single file database that stores information in the form of JSON. It is a remote database, so it's usually accessed through the Internet.

In this stage, you need to simulate a database that can store text information in an array of size 100. From the start of the database, every cell contains an empty string. Users can save strings in the cells, read the information from these cells, and delete that information if needed. After a string has been deleted, that cell should contain an empty string.

The user can use the set, get, or delete commands.

##### Objectives
After set, a user should specify a number (1-100) and the text that will be saved to the cell. If the index is wrong, the program should output ERROR, otherwise, output OK. If the specified cell already contains information, it should be overwritten.
After get, a user should specify the cell number from which they want to get information. If the cell is empty or the index is wrong, the program should output ERROR; otherwise, the program should output the content of the cell.
After delete, the user should specify the number of the cell. If the index is wrong, the program should output ERROR; otherwise, output OK. If the string is empty, you don't have to do anything.
To exit the program, a user should enter exit.
Your program should run from the main function of the server package.

---

### Stage 2️⃣ Connect it to a server
##### Description
Usually, online databases are accessed through the Internet. In this project, the database will be on your computer, but it will still be run as a separate program. The client who wants to get, create, or delete some information is a separate program, too.

We will be using a socket to connect to the database. A socket is an interface to send and receive data between different processes. These processes can be on the same computer or different computers connected through the Internet.

To connect to the server, the client must know its address, which consists of two parts: IP-address and port. The address of your computer is always "127.0.0.1". The port can be any number between 0 and 65535, but preferably greater than 1024.

Let's take a look at this client-side code:

String address = "127.0.0.1";
int port = 23456;
Socket socket = new Socket(InetAddress.getByName(address), port);
DataInputStream input = new DataInputStream(socket.getInputStream());
DataOutputStream output = new DataOutputStream(socket.getOutputStream());
The client created a new socket, which means that the client tried to connect to the server. Successful creation of a socket means that the client found the server and managed to connect to it.

After that, you can see the creation of DataInputStream and DataOutputStream objects. These are the input and output streams to the server. If you expect data from the server, you need to write input.readUTF(). This returns the String object that the server sent to the client. If you want to send data to the server, you need to write output.writeUTF(stringText), and this message will be sent to the server.

The server created a ServerSocket object that waits for client connections. When a client connects, the method server.accept() returns the Socket connection to this client. After that, you can see the creation of DataInputStream and DataOutputStream objects: these are the input and output streams to this client, now from the server side. To receive data from the client, write input.readUTF(). To send data to the client, write output.writeUTF(stringText). The server should stop after responding to the client.

##### Objectives
In this stage, implement the simplest connection between one server and one client. The client should send the server a message: something along the lines of Give me a record # N, where N is an integer number. The server should reply A record # N was sent! to the client. Both the client and the server should print the received messages to the console. Note that they exchange only these texts, not actual database files.

Before a client connects to the server, the server output should be Server started!.

Note: the server and the client are different programs that run separately. Your server should run from the main function of the /server package, and the client should run from the main method of the /client package. To test your program you should run the server first so a client can connect to the server.

##### Example
The server should output something like this:
```
Server started!
Received: Give me a record # 12
Sent: A record # 12 was sent!
```
The client should output something like this:
```
Client started!
Sent: Give me a record # 12
Received: A record # 12 was sent!
```

---

### Stage 3️⃣ Add new functionalities
##### Description
In this stage, you will build upon the functionality of the program that you wrote in the first stage. The server should be able to receive messages get, set, and delete with an index of the cell. You also need to extend the database to 1000 cells (1-1000).

There is no need to save files on the hard drive, so if the server reboots, all the data will be lost. The server should serve one client at a time in a loop, and the client should only send one request to the server, get one reply, and exit. After that, the server should wait for another connection.

##### Objectives
Since the server cannot shut down by itself and testing requires the program to stop at a certain point, you should implement a way to stop the server. When the client sends exit, the server should stop. In a normal situation when there's no testing needed, you shouldn't allow this behavior.

To send a request to the server, the client should get all the information through command-line arguments. These arguments include the type of the request (set, get, or delete), the index of the cell, and, in the case of the set request, a text.

There is a good library to parse all the arguments called JCommander. It is included in our project setup, so you can use it without any installation. Before you get started with it, we recommend you check out a JCommander tutorial.

The arguments will be passed to the client in the following format:

-t set -i 148 -m Here is some text to store on the server
-t is the type of the request, and -i is the index of the cell. -m is the value to save in the database: you only need it in case of a set request.

The server and the client are different programs that run separately. Your server should run from the main method of the /server/Main class, and the client should run from the main method of the /client/Main class.

##### Example
The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

Starting the server:
```
java Main

Server started!
```
Starting the clients:
```
java Main -t get -i 1

Client started!

Sent: get 1

Received: ERROR
```
---

### Stage 4️⃣ Start work with JSON
##### Description
In this stage, you will store the database in JSON format. To work with JSON, we recommend using the GSON library made by Google. It is also included in our project setup. It is a good idea to get familiar with the library beforehand: see zetcode.com for some explanations!

In this stage, you should store the database as a Java JSON object.

The keys should be strings (no more limited integer indexes), and the values should be strings, as well.

Example of JSON database:
```
{
    "key1": "String value",
    "key2": 2,
    "key3": true
}
```
Also, you should send to the server a valid JSON (as a string) which includes all the parameters needed to execute the request. Below are a few examples for the set, get, and delete requests. Don't worry about multiple lines: the GSON library can represent them as a single line. Also, don't worry about extra spaces before and after quotes.

Here is what the set request format should look like:
```
{ "type": "set", "key": "Secret key", "value": "Secret value" }
```
The responses should be in the JSON format. Please consider the examples below.
```
{ "response": "OK" }
```
The get request
```
{ "type": "get", "key": "Secret key" }
```
The delete request
```
{ "type": "delete", "key": "Key that doesn't exist" }
```
In the case of a get request with a key stored in the database:
```
{ "response": "OK", "value": "Secret value" }
```
In the case of a get or delete request with a key that doesn't exist:
```
{ "response": "ERROR", "reason": "No such key" }
```

##### Objectives
Implement a Java JSON object to store the database records.

Implement the set, get, and delete requests and the OK and ERROR responses. Don't worry about multiple lines: the GSON library can represent them as a single line. Also, don't worry about extra spaces before and after quotes.

The arguments will be passed to the client in the following format:

-t set -k "Some key" -v "Here is some text to store on the server"
-t is the type of the request, and -k is the key. -v is the value to save in the database: you only need it in case of a set request.

---

### Stage 5️⃣ Manage multiple requests
##### Description
In this stage, improve your client and server by adding the ability to work with files. The server should keep the database on the hard drive file and update only after setting a new value or deleting one.

Let's think about another important aspect: when your database server becomes very popular, it won’t be able to process a lot of requests because it can only process one request at a time. To avoid that, you can parallelize the server's work using executors, so that every request is parsed and handled in a separate executor's task. The main thread should just wait for incoming requests.

For this kind of functionality, you need synchronization because all your threads will work with the same file. Even after parallelizing, you need to preserve the integrity of the database. Of course, you can't write the file in two separate threads simultaneously, but if no one is currently writing to the file, a lot of threads can read it, and no one can interrupt the other since no one is modifying it. This behavior is implemented in java.util.concurrent.locks.ReentrantReadWriteLock class. It allows multiple readers of the resource but only a single writer. Once a writer locks the resource, it waits until all the readers finish reading and only then starts to write. The readers can freely read the file even though other readers locked it, but if the writer locks the file, no readers can read it.

To use this class, you need two locks: read lock and write lock. See the snippet below:

ReadWriteLock lock = new ReentrantReadWriteLock();
Lock readLock = lock.readLock();
Lock writeLock = lock.writeLock();
Every time you want to read the file, invoke readLock.lock(). After reading, invoke readLock.unlock(). Do the same with writeLock, but only when you want to change the data.

Here are some examples of the input file contents:
```
{"type":"set","key":"name","value":"Kate"}
{"type":"get","key":"name"}
{"type":"delete","key":"name"}
```
##### Objectives
The server should keep the database on the hard drive in a db.json file which should be stored as the JSON file in the /server/data folder.
Use executors at the server in order to simultaneously handle multiple requests. Writing to the database file should be protected by a lock as described in the description.
Implement the ability to read a request from a file. If the -in argument is followed by the file name provided, read a request from that file. The file will be stored in /client/data.

### Stage 6️⃣ Store JSON objects in your database
##### Description
Improve your database in this stage. It should be able to store not only strings but any JSON objects as values.

The key should not only be a string since the user needs to retrieve part of the JSON value. For example, in the code snippet below, the user wants to get only the surname of the person:
```
{
    ... ,

    "person": {
        "name": "Adam",
        "surname": "Smith"
    }
    ...
}
```
Then, the user should type the full path to this field in a form of a JSON array: ["person", "surname"]. If the user wants to get the full person object, then they should type ["person"]. The user should be able to set separate values inside JSON values. For example, it should be possible to set only the surname using a key ["person", "surname"] and any value including another JSON. Moreover, the user should be able to set new values inside other JSON values. For example, using a key ["person", "age"] and a value 25, the person object should look like this:
```
{
    "person": {
        "name": "Adam",
        "surname": "Smith",
        "age": 25
    }
}
```
If there are no root objects, the server should create them, too. For example, if the database does not have a "person1" key but the user set the value {"id1": 12, "id2": 14} for the key ["person1", "inside1", "inside2"], then the database will have the following structure:

```
    "person1": {
        "inside1": {
            "inside2" : {
                "id1": 12,
                "id2": 14
            }
        }
```
The deletion of objects should follow the same rules. If a user deletes the object above by the key ["person1", "inside1", "inside2], then only "inside2" should be deleted, not "inside1" or "person1". See the example below:
```
{
    "person1": {
        "inside1": { }
    }
}
```
##### Objectives
Enhance your database with the ability to store any JSON objects as values as portrayed at the description.
##### Examples
Starting the server:
```
> java Main
Server started!
```
There is no need to format JSON in the output.

Starting the clients:
```
> java Main -t set -k 1 -v "Hello world!" 
Client started!
Sent: {"type":"set","key":"1","value":"Hello world!"}
Received: {"response":"OK"}
> java Main -in setFile.json 
Client started!
Sent:
{
   "type":"set",
   "key":"person",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster",
         "year":"2018"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"87"
      }
   }
}
Received: {"response":"OK"}
```
```
> java Main -in getFile.json 
Client started!
Sent: {"type":"get","key":["person","name"]}
Received: {"response":"OK","value":"Elon Musk"}
```
```
> java Main -in updateFile.json 
Client started!
Sent: {"type":"set","key":["person","rocket","launches"],"value":"88"}
Received: {"response":"OK"}
```
```
> java Main -in secondGetFile.json 
Client started!
Sent: {"type":"get","key":["person"]}
Received:
{
   "response":"OK",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster",
         "year":"2018"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"88"
      }
   }
}
```
</details>

## Tests coverage ✅

<details><summary>Click to expand</summary>
<img width="1203" alt="Screenshot 2024-04-06 at 20 58 08" src="https://github.com/oskarklkt/json-database/assets/117487714/71fa8558-b35b-4fcb-8bbd-913b795f95be">
</details>




