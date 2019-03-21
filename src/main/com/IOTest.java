import java.io.*;

public class IOTest {
    public static void main(String[] args){
        File file = new File("C:/java/ideaProject/11.txt");
        try {
            FileInputStream in = new FileInputStream((file));
            //BufferedReader br = new BufferedReader(new InputStreamReader(in),1024);
            //String s = br.readLine();
            BufferedInputStream bis = new BufferedInputStream(in,1024);
            System.out.println(bis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
