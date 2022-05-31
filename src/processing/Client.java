package processing;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.LinkedList;

import game.Game;

public class Client {
    String IPAddress;
    
    public Client(String IPAddress) {
       this.IPAddress = IPAddress;
    }
    
    public void sendMessage(String message) {
        try{
            Socket socket = new Socket(IPAddress, 5000);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.write(message);
            output.flush();

            output.close();
            socket.close();
        } catch(ConnectException e){
            //System.out.println("timeOut");
            Game.getInstance().setConnectError(true);
        } catch(Exception e){
            System.out.println(e);
            Game.getInstance().setConnectError(true);
        }
    }

    
    public void sendMessage(LinkedList<String> listString) {
        try{
            Socket socket = new Socket(IPAddress, 5000);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.write("end\n");
            output.flush();
            
            writeList(socket,listString);
//                System.out.println("end catch");
            
            output.close();
            socket.close();
        } catch(ConnectException e){
            //System.out.println("timeOut");
            Game.getInstance().setConnectError(true);
        } catch(Exception e){
            System.out.println(e);
            Game.getInstance().setConnectError(true);
        }
    }
    
    private void writeList(Socket socket,LinkedList<String> listString) throws Exception{
//      Scanner input = new Scanner(socket.getInputStream());
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      try {
          //読み込み行
          String line = "";
    
          //読み込み行数の管理
          int i = 0;
          
          //1行ずつ読み込みを行う
          while ((line = input.readLine()) != null) {
              
              //先頭行は列名
              if (i == 0) {
                  listString.add(line);
              } else {
                  listString.add(line);
              }
//              System.out.println(line);
          i++;
          }
      } catch (Exception e) {
    //      e.printStackTrace();
      } finally {
          

      }
      
      input.close();
      Game.getInstance().setCsvEnd(true);
    }
}