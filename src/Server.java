import java.util.Properties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public class Server {
    // Client Handler's List
    private static Set<Clients> ClientHandlers = Collections.synchronizedSet(new HashSet<>());
    private static Properties prop = new Properties();
    public static int connected = 0;
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(4999);

        System.out.println("Server Initiated");
        while (true) {
            Socket user = server.accept();
            Clients client = new Clients(user);
            ClientHandlers.add(client);
            (new Thread(client)).start();
            connected++;
        }
        
    };

    private static void initializer() throws IOException {
        prop.load(Files.newInputStream(Path.of("./bin/.env")));

    }

    public static void Broadcaster(String msg, Clients sender) {
        synchronized(ClientHandlers) {
            for (Clients client : ClientHandlers) {
                if (client != sender) client.op.println(msg);
            }
        }
    }

    static class Clients implements Runnable {
        private Socket con;
        private String name; // Username
        private BufferedReader ip;
        private PrintWriter op;
        private 
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

                op.println("Welcome to the server, Please Enter your username => ");
                name = ip.readLine();
                System.out.println("User "+name+" has joined the server. Time:"+Long.toString((new Date()).getTime()));

                String message;
                while ((message = ip.readLine()) != null) {
                    Broadcaster(message, this);
                }

                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
    }




}


