import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server {
    private final static int port = 8888;

    public static HashMap<Socket, Integer> playersjoined = new HashMap<>();
    public static TreeMap<Integer, Boolean> gs = new TreeMap<>();  //playerid and whohasball, gamestate gs
    private static int playerid = 0;


    public static List<Integer> players() {    //list of players
        List<Integer> l = new ArrayList<Integer>();
        for (int v : playersjoined.values()) {
            l.add(v);               //adds in lists when player join
        }
        return l;
    }

    public static void main(String[] args) {
        System.out.println("Waiting for connections...");
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            while (true) {
                Socket socket = ss.accept();
                playerid++;
                playersjoined.put(socket, playerid); //adds player to hashmap
                if (Server.gs.isEmpty()) {          //checks if first player has been added to the tree map
                    Server.gs.put(playerid, true);
                    System.out.println("First player is here! Ball is passed on to PlayerID: " + playerid);
                } else {
                    Server.gs.put(playerid, false);
                }
                System.out.println("PlayerID: " + playerid + ", " + " is Successfully Connected .");
                System.out.println("CONNECTED PLAYERS LIST: ");
                System.out.println(players());


                Thread thread = new PlayerHandler(socket, playerid);
                thread.start();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
