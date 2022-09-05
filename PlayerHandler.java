import java.io.*;
import java.net.Socket;
import java.util.*;
import  java.util.ArrayList;

public class PlayerHandler extends Thread{
    private PrintWriter writer;
    private Socket socket;
    public int playerid;
    Scanner reader;
    Ball ball = new Ball();

    public PlayerHandler(Socket socket, int playerid) throws IOException {

        this.socket = socket;
        this.playerid = playerid;
    }
    @Override
    public void run() {
        try {
            reader = new Scanner(socket.getInputStream()); //gets input
            writer = new PrintWriter(socket.getOutputStream(), true);


            writer.println(this.playerid);
            List<Integer> listplayers = new ArrayList<Integer>();
            listplayers = Server.players();
            writer.println(listplayers.size());
            for (int players : listplayers) {
                writer.println(players);
            }
            writer.println(ball.whohasball());

            while (true) {
                if (true == ball.doesplayerhasball(this.playerid)) {
                    writer.println(true);
                    int input = Integer.parseInt(reader.nextLine());
                    if (input > 0) {
                        // pass ball to other player
                        ball.passBalltoplayer(input, this.playerid);
                    }
                    if(input == 0){
                        List<Integer> listplayers2 = Server.players();
                        writer.println(listplayers2.size());
                        for (int players : listplayers2) {
                            writer.println(players);
                        }
                    } }
                if(false == ball.doesplayerhasball(this.playerid)){
                    writer.println(false);
                    String line = reader.nextLine();
                    switch(line){
                        case "w":
                            writer.println(ball.whohasball());
                            List<Integer> listplayers2 = Server.players();
                            writer.println(listplayers2.size());
                            for (int players : listplayers2) {
                                writer.println(players);
                            }
                         break;
                        default:
                            throw new Exception(" Unknown command: " + line);
                    }
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            writer.println("Error ---- Wrong input Disconnecting player please rejoin" + e.getMessage());
        } finally{
            try {
                DiconnectPlayer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //disconnect player form the server
    public void DiconnectPlayer() throws IOException {
        System.out.println("\nPlayer: " + this.playerid + ", " + " Connection closed.");
        Server.playersjoined.remove(this.socket, this.playerid); // removes player

        Ball.removeBall(this.playerid);
        Server.gs.remove(this.playerid); //rwemeoves player from gs
        ball.disconnecting();

        System.out.println();
        System.out.println("CONNECTED PLAYERS: ");
        if (Server.players().isEmpty()) {
            System.out.println("Waiting for players to join....");
            System.out.println();
        } else {
            System.out.println(Server.players());
        }
        this.socket.close();

    }



}




