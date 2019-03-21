import java.io.*;
import java.nio.CharBuffer;
import java.util.Date;

public class IOtest1 {
    public static void main(String[] args) {
        String path = "C:/Users/PC/Desktop/docker.docx";

        Date d1 = new Date();
        String result1 = BrTest(path);//字符流读取
        Date d2 = new Date();
        System.out.println("==========>>>br cost:"+(d2.getTime()-d1.getTime())+"ms");
//      System.out.println(result1);

        d1 = new Date();
        String result2 = IsTest(path);//字节流读取
        d2 = new Date();
        System.out.println("==========>>>is cost:"+(d2.getTime()-d1.getTime())+"ms");
//      System.out.println(result2);

    }

    /**
     *
     * @Title: IsTest
     * @Desc: 字节流
     *
     *  @param path
     *  @return 参数
     *
     */
    public static String IsTest(String path) {
        InputStream is = null;
        StringBuilder sb = new StringBuilder();//切忌不要直接用String直接拼接文本。。效率太低，每次要new新对象，用StringBuilder，StringBuffer都可以
        try {
//          is = new FileInputStream(path);
            //这里用Buffered 缓存，效率有所提升
            is = new BufferedInputStream(new FileInputStream(path));
            byte[] buf = new byte[1024];

            sb = new StringBuilder();
            int len = is.read(buf);
            OutputStream fos = new FileOutputStream("C:/java/ideaProject/docker1.docx");
            BufferedOutputStream bos = new BufferedOutputStream(fos,1024);
            while (len != -1) {
                //这里需要注意：不能直接new String(buf),循环到文本最后的时候，如果只剩下100个字节，buf（上面1024字节）没有写满，buf只会替换前面100个字节，后面924个字节还是之前的。所以在最后一次循环的时候要有个范围才行。
//                sb.append(new String(buf,0,len));
                bos.write(buf,0,len);
                bos.flush();
                len = is.read(buf);
            }
            //写入文本
           /* OutputStream fos = new FileOutputStream("C:/java/ideaProject/22.txt");
            BufferedOutputStream bos = new BufferedOutputStream(fos,1024);
            String ss = "只适合或或或";
            bos.write(ss.getBytes(),0,ss.getBytes().length);
            bos.flush();
            bos.close();*/
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {//记着在finally中关闭IO
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     *
     * @Title: BrTest
     * @Desc: 字符流
     *
     *  @param path
     *  @return 参数
     *
     */
    public static String BrTest(String path) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();//同样，字符串操作多就不要用String了
        try {
            br = new BufferedReader(new FileReader(path));

            //readline这是网上最常见的读取方式，然而我经过实测，发现效率并不是很高，但也没太大影响
//          String str = null;
//          while ((str = br.readLine()) != null) {
//              sb.append(str+"\n");
//          }
            //这里使用CharBuffer的话比直接readline快很多
            CharBuffer cb = CharBuffer.allocate(1024);
            while(br.read(cb)!=-1){
                cb.flip();//注意，这里不调会死循环。。。flip()将缓存字节数组的指针设置为数组的开始序列即数组下标0。这样就可以从buffer开头，对该buffer进行读取。
                sb.append(cb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {//关掉IO
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
