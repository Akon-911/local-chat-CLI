import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class App {

    public static void main(String[] args) throws Exception {
        Socket con = new Socket("localhost",4999);
        BufferedReader ip = new BufferedReader(new InputStreamReader(con.getInputStream()));
        PrintWriter op = new PrintWriter(con.getOutputStream(), true);
        Scanner inp = new Scanner(System.in);
        System.out.println(ip.readLine());
        String user = inp.next();
        op.println(user);
        System.out.print(user+":");
        try {
            String msg;
            while ((msg = inp.next())!= null) {
                if (msg.equals(">exit"))  { 
                    System.out.println("Thanks for using this chatroom.");break; 
                }
                else { 
                    System.out.println("\n"+user+": ");
                }
            }
        } catch (Exception E) {
            E.printStackTrace();
            con.close();
            inp.close();
        }

    }
}
