
import java.util.Scanner;

public class Player {
    public static void main(String[] args) {
        try (Host player = new Host()){
           int readplayerID = player.inputplayer();

            System.out.println("You are Player: " + readplayerID + "\n");
            System.out.println("PLAYERS WHO ARE CONNECTED : ");
            int[] numplayer = player.getNumberOfPlayers();
            for (int i: numplayer)
                System.out.println("Connected : " + i);
            System.out.println("Player: " + player.ballowner() + " currently has the ball.");

            while(true) {
                boolean playerhasBall = player.checkPlayerHasBall();
                if(playerhasBall) {
                    System.out.println("YOU ARE THE NEW OWNER OF THE BALL, who would you like to pass, press 0 to see list of active players");
                    Scanner input = new Scanner(System.in);
                    String playerid = input.nextLine();
                    player.inputplayer(playerid);
                    if (playerid.equals("0")) {
                        int[] playerlist = player.getNumberOfPlayers();
                        for (int i : playerlist)
                            System.out.println(i);
                    }
                }
                if(!playerhasBall){
                    System.out.println("SOMEONE ELSE HAS THE BALL, enter 'w' to know who");
                    Scanner input= new Scanner(System.in);
                    String string= input.nextLine();
                    player.inputplayer(string);
                    System.out.println("Player: " + player.ballowner() + " currently has the ball.");
                    int[] playerlist = player.getNumberOfPlayers();
                    for (int i : playerlist)
                        System.out.println("Players : " + i);
                } }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}





