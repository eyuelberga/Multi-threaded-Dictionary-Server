# Multi-threaded Dictionary Server

## Architecture

The system follows a client-server architecture. The server uses a thread pool to manage
incoming connections. Every incoming request is passed to the thread pool for execution
when a thread in the pool becomes idle.

The main reason for choosing the thread pool server architecture over the a simple multi-
threaded server is to control the machine resources more effectively. By controlling the
maximum number of threads we can minimize resource depletion. Using thread pool, we can
queue requests and process concurrently only a limited amount. This will also benefit the
client, as concurrently executing many requests will slow down all requests processed.

## Design

### Server

The major jobs of the server is to open a socket connection with the client, take the client
request, process the request and send response with the appropriate data. The design decisions
on the server is to make it as loosely coupled as possible to allow for new implementations to
be integrated without much refactoring.

**Communication handling**

The Server opens up a port to accept connections. When a connection is established a new
SocketHandler thread is created and added to the thread pool. The SocketHandler handles
requests form client and sends response back and closes the connection.

**Database**

Persistent data operations for the server are provided by the DictionaryRepository interface.

**Message exchange**

The MessageExchange interface provides the means to implement a messaging protocol
between the client and server.

**Request processing**

By implementing the DictionaryService with an implementation of the DictionaryRepository,
a request processing scheme can be built for the server. Also, implementing the
MethodResolver utility interface using a DictionaryService implementation, will result in a
much easier request handling. Every response returned must provide a code from the `Codes`
enumeration to enhance communication between the server and client.

![server step 1](/images/server-class.png)

### Client
The client sends request to server and display the response to the user. Same as the server, the
design aims to create a loosely coupled code for further extensions.

**Communication handling**

For every request sent to the server, the SocketHandler class is responsible for creating a
socket connection, sending the request and after response has been received close the
connection.

**Message exchange**

The MessageExchange interface provides the means to implement a messaging protocol
between the client and server. Same implementations must be used to avoid parsing errors.

**Response processing**

The ClientUtil class provides a function to resolve response received form the server, using the
code sent with the response.

![server step 1](/images/client-class.png)

## Implementation

### Server

**Folder structure**

- config – configuration classes and enum classes for response codes and messages
- exceptions – custom exception for the server
- messaging – message exchange protocol implementations
- models – business logic and persistent model classes
- repositories – classes for persistent data access and modification
- server – classes for client communication handling
- ui – GUI logic and resources
- utils – utility classes

**Implementation details**

- JsonDbDictionaryRepository is implemented using JsonDB, a lightweight JSON based
file database. It is also a singleton.
- JsonMessageExchange implemented using the Jackson library and exchange messages
using JSON.
- GUI is built using JavaFX and Jfoenix library

### Client

**Folder structure**

- config – configuration classes and enum classes for response codes and messages
- controllers – classes for interaction between the graphical user interface and client logic
- exceptions – custom exception for the server
- messaging – message exchange protocol implementations
- models – business logic and persistent model classes
- resources – files for user the graphical user interface
- utils – utility classes

**Implementation details**

- JsonMessageExchange implemented using the Jackson library and exchange messages
using JSON.
- GUI is built using JavaFX and Jfoenix library

## Usage

### Running the Jar files

The jar files for both client and server depend on the JavaFX and the Jfoniex libraries, so the
modules need to be added before running the jar file.

```shell script
java --module-path <<path to lib>> --add-modules
javafx.controls,javafx.fxml,com.jfoenix --add-exports
javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix --add-exports
javafx.controls/com.sun.javafx.scene.control=com.jfoenix --add-exports
javafx.base/com.sun.javafx.binding=com.jfoenix --add-exports
javafx.graphics/com.sun.javafx.stage=com.jfoenix --add-exports
javafx.base/com.sun.javafx.event=com.jfoenix -jar <<path to Jar file>>
```
- <<Path to lib>> – path to library file for javaFX and Jfoenix
- <<path to Jar file>> – path to the Server.jar file

### Usage Guide

#### Server

**1. Configure the server**

![server step 1](/images/server-1.png)

before you start the server you need to specify the port and the size of the thread pool. The
thread pool size determines how many concurrent requests the server can process. After you
start the server you should see the screen below:

![server step 1](/images/server-2.png)

**2. Observe server status and logs**

Once the server has started you can see its status on the stats tab, which shows how many
requests the server is processing, and the connection limit, which is the size of the thread pool.

![server step 1](/images/server-3.png)

You can also view the log form the server in the log tab. Every activity done by the server can
be found in the table.

![server step 1](/images/server-4.png)

**3. Stopping the server**

Once you are done you can stop the server form the server tab by clicking on the stop server
button.

#### Client

**1. Configure server settings**

when you open the client application, you will land at the home page, before you make
requests to the server make sure to configure the server settings using the configure button
located on the top right.

![server step 1](/images/client-1.png)

The configuration windows allows you to enter the host name of the server and the port.

**2. Using the features**

The dictionary client provides adding, searching, updating and deleting words.

**Search**

you can search for words by typing them on the search field and pressing enter.

![server step 1](/images/client-2.png)

**Add**

To add new word click on the add button located on the left. A new window with a form will
be opened, after you fill out the form click add.

![server step 1](/images/client-3.png)

**Update**

You can update a word you have searched for using the edit button bellow the word’s
meaning

**Delete**

You can delete a word you have searched for using the delete button bellow the word’s
meaning.


**3. Closing the client**

you can close the client by clicking on the windows close button.

