import java.util.List;
import java.util.Map;

public class Ball {

    public int whohasball() {
        int playerID = 0;
        for (Map.Entry e : Server.gs.entrySet()) {  //for loop in gs
            if ((boolean) e.getValue()) {    //if player has ball is true
                playerID = (int) e.getKey();  //set player id to the key of entry for which the value is true
            }
        }
        return playerID;
    }
    public boolean doesplayerhasball(int playerID) { //the player we are comparing to
        int playerHasBall = whohasball();           // player has the ball
        if (playerHasBall == playerID) {            // if player has ball return true
            return true;
        } else {
            return false;
        }
    }
    public static boolean doplayerExist(int playerID) {
       boolean counter = false;
        for (Integer entryID : Server.gs.keySet()) { // for loop in gs keys
            if (entryID == playerID) {          // if existing key exists
                counter = true;                    // return true
                break;
            }
        }
        return counter;
    }
    public static void removeBall(int playerID)
    {
        Server.gs.replace(playerID, false); // false if there is no ball
    }
    public void passBalltoplayer(int recieve, int curplayerID) {
        // validating the player to pass the ball
        if (doplayerExist(recieve) && recieve != curplayerID) {  //if player exist and wants to pass to other player
            System.out.println("Player: " + curplayerID + " passes ball to Player: " + recieve + ".");
            Server.gs.replace(recieve, true); //replaces player value with true that he has the ball
            removeBall(curplayerID);
        } else if (recieve == curplayerID) { // if player wants to pass to himself
            System.out.println("Player: " + recieve + " is throwing the ball back to him...");
        } else {
            System.out.println("Player: " + recieve + " does not exist, returning ball!"); //if p[layer doesnot exist
            Server.gs.replace(curplayerID, true);
        }
    }
    //if player with the ball has disconneted then pass the ball to last player
    public void disconnecting() {
        if (Server.gs.containsValue(false) && Server.gs.size() >= 1) {  //checks if no one has the ball and there are more than 1 player in the game
            Map.Entry<Integer, Boolean> l = Server.gs.lastEntry(); // finds last player who had the ball
            System.out.println("Since Player with ball disconnected, passing ball to Player: " + l.getKey());
            Server.gs.replace(l.getKey(), true); // uses last key and replaces its value with true to pass the ball to last player
            List<Integer> listofplayers = Server.players();
            for (int players : listofplayers) {
                System.out.println(players);
            }
        }
        if (Server.gs.isEmpty()) {
            System.out.println("No one left in the lobby to play");
        }
    }
}
