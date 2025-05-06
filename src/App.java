import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;


public class App {

    public static String user;
    public static Socket con;
    public static void main(String[] args) throws Exception {
        con = new Socket("localhost",4999);
        BufferedReader ip = new BufferedReader(new InputStreamReader(con.getInputStream()));
        PrintWriter op = new PrintWriter(con.getOutputStream(), true);
        Scanner inp = new Scanner(System.in);
        System.out.print(ip.readLine());
        user = inp.nextLine();
        System.out.println("\nRegestering....");
        Thread reader = new Thread(new Reader(ip));
        reader.start();
        op.println(user);

        try {
            String msg;
            while ((msg = inp.nextLine())!= null) {
                if (msg.equals(">exit"))  { 
                    System.out.println("Thanks for using this chatroom.");
                    op.close();
                    con.close();
                    break;
                }
                op.println(msg);
            }
        } catch (Exception E) {
            E.printStackTrace();
            inp.close();
        }
    }

    static class Reader implements Runnable {

        private BufferedReader ip;

        public Reader(BufferedReader inputBufferedReader) {
            this.ip = inputBufferedReader;
        }

        public void run() {
            String msg;
            try {
                while (!con.isClosed()) {
                    msg = ip.readLine();
                    if (msg != null) {
                        System.out.println(msg);
                    }
                }
            } catch (SocketException Err) {
                if (Err.getMessage().contains("Socket closed")) {
                    System.out.println("Disconnected from the server");
                }
            } catch (Exception E) {
                E.printStackTrace();
            } finally {
                System.out.println("The Reader is now closed.");
                try {
                    ip.close();
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }

}
