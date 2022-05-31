package network;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class network {
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("10.0.1.10", 5000);
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            String message = "kakunin";
            output.println(message);
            output.flush();
            
            Scanner input = new Scanner(socket.getInputStream());
    //            pw.print(input.nextLine());
    //            pw.close();
            
            try {
                //読み込み行
                String line = "";
    
                //読み込み行数の管理
                int i = 0;
                
                //1行ずつ読み込みを行う
                while ((line = input.nextLine()) != null) {
                    
                    //先頭行は列名
                    if (i == 0) {
                        System.out.println(line);
                    } else {
                        System.out.println(line);
                    }
                i++;
                }
            } catch (Exception e) {
    //                e.printStackTrace();
            } finally {

            }
            
            input.close();
            output.close();
            socket.close();
        } catch(Exception e){
            System.out.println(e);
        }
    }
}
