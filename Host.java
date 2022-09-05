import java.io.PrintWriter;
import java.util.Scanner;
import java.net.Socket;


public class Host implements AutoCloseable{

    private final PrintWriter write;
    public final Scanner inputread;
    final int port = 8888;

    public Host() throws Exception {
        Socket s = new Socket("localhost", port);
        inputread = new Scanner(s.getInputStream());
        write = new PrintWriter(s.getOutputStream(), true);

    }
    public int[] getNumberOfPlayers(){
        String str = inputread.nextLine();
        int players = Integer.parseInt(str);
        int[] playerslist = new int[players]; // Reading the player numbers
        for (int i = 0; i < players; i++) {
            str = inputread.nextLine();
            playerslist[i] = Integer.parseInt(str);
        }
        return playerslist;
    }
    public void inputplayer(String string) //function overload
    {
        write.println(string);
    }

    public int inputplayer(){           //function overload
        int playerid = Integer.parseInt(inputread.nextLine());
        return playerid;
    }

    public int ballowner(){
        return Integer.parseInt(inputread.nextLine());
    }

    public boolean checkPlayerHasBall()
    {
        return Boolean.parseBoolean(inputread.nextLine());
    }

    @Override
    public void close(){
        inputread.close();
        write.close();
    }
}