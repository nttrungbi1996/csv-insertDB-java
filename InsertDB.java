import java.io.*;
import java.sql.*;

public class InsertDB {
    private final static String inputCsvFile = "C:\\Users\\MGT社員\\Desktop\\郵便番号データ01.CSV";

    public static void main(String[] args) {
        // TODO 自動生成されたメソッド・スタブ
        String host = "127.0.0.1";
        String db = "mydb";
        String user = "root";
        String pass = "123qwe";
        String tbl = "sample01";

        String url = null;
        Connection con = null;
        PreparedStatement pst = null;
        String insertSql = "INSERT INTO " + tbl
                + " (A,B,C,D,E,F,G,H,I) VALUES (?,?,?,?,?,?,?,?,?)";

        File fileInput = new File(inputCsvFile);
        boolean isFirstLine = true;        // 最初の行はヘッダ
        int columnStart = 0;
        int columnEnd = 9;

        try (FileInputStream input = new FileInputStream(fileInput);
             InputStreamReader streamInput = new InputStreamReader(input, "SJIS");
             BufferedReader bufferInput = new BufferedReader(streamInput)) {

            Class.forName("org.mariadb.jdbc.Driver");
            url = "jdbc:mariadb://" + host + "/" + db;  //"?useUnicode=true&characterEncoding=utf8";
            con = DriverManager.getConnection(url, user, pass);
            pst = con.prepareStatement(insertSql);

            String str;

            while ((str = bufferInput.readLine()) != null) {
                if (isFirstLine) {
                    // 最初の行はヘッダー行なので無視する
                    isFirstLine = false;
                    continue;
                }
                //		    	byte[] b = str.getBytes();
                //		    	str = new String(b, "UTF-8");

                String[] col = str.split(",", -1);

                for (int i = columnStart; i < columnEnd; i++) {
                    pst.setString(i + 1, col[i]);
                }

                pst.executeUpdate();
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            System.out.println("終わりました。");
        }
    }
}