import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.util.Properties;
import java.util.Date;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public class Server {

    // Client Handler's List
    private static Set<Clients> ClientHandlers = Collections.synchronizedSet(new HashSet<>()); 
    // Client Handler (ArrayList would work but since it ain't built thread safe then I am not using it)


    // private static Properties prop = new Properties(); // To load ENV File from the bin folder
    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(4999); // Average Socket number that came in my mind

        System.out.println("Server Initiated");
        while (true) {
            Socket user = server.accept();
            System.out.println("New Client!\n"+user.getPort()+"");
            Clients client = new Clients(user);
            ClientHandlers.add(client);
            (new Thread(client)).start();
        }
        
    };

    // To load the credentials which will be worked upon later....
    // private static void initializer() throws IOException {
    //     prop.load(Files.newInputStream(Path.of("./bin/.env")));

    // }

    // When Client Disconnects
    public static void disconnect(Clients client) {
        String msg = "User "+client.name+" is now offline.";
        System.out.println(msg);
        ClientHandlers.remove(client);
    }

    // Broadcaster as the name says, sending the message to all clients
    public static void Broadcaster(String msg, Clients sender) {
        System.out.println(sender.name+": "+msg);
        synchronized(ClientHandlers) {
            for (Clients client : ClientHandlers) {
                if (client != sender)  {
                    client.op.println(msg);
                }
            };
        };
    };

    static class Clients implements Runnable {
        private Socket con;
        private String name; // Username
        private BufferedReader ip;
        private PrintWriter op;
        Clients(Socket new_con) {
            con = new_con;
            try {
                this.ip = new BufferedReader(new InputStreamReader(con.getInputStream()));
                this.op = new PrintWriter(con.getOutputStream(),true);
            } catch (IOException E) {
                E.printStackTrace();
            }
        }
        @Override
        public void run() {

            try {

                // The welcoming message + registering name and notifying formalities
                op.println("Welcome to the server, Please Enter your username => ");
                name = ip.readLine();
                
                op.println("\nYou have now successfully joined the server as "+name+"\nType \">exit\" to leave the chat");
                
                Broadcaster("User "+name+" has joined the server. Time:"+Long.toString((new Date()).getTime()), this);
                System.out.println("User "+name+" has joined the server. Time:"+Long.toString((new Date()).getTime()));

                // The main loop of recieving and sync of messages across all clients
                String message;
                while ((message = ip.readLine()) != null) {
                    Broadcaster(name+": "+message, this); 
                }

                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Broadcaster(name+" is now disconnected from the server.", this);
                disconnect(this);
            }
        }
    
    }




}


