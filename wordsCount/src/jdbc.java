 


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

  * <pre>
 * The jdbc class.
* The program is to load data from a database wordOccurrences,
 * Its to be used as a module for the wordCounter app
 * </pre>
 *  * @author Gregory Lauture
 *  * @version 1.0
 *  * @since 2021 -12-201
 *  */


public class jdbc {
    private static final String dbUrl ="jdbc:mysql://localhost/wordOccurrences";
    private static final String dbUsername ="root";
    private static final String dbPassword="Deschamps200*.*";


    private Connection conn;

    /**
     * This will create a new dobject atabase
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public jdbc()  throws SQLException, ClassNotFoundException {
        Statement statement;
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        String sql = "CREATE DATABASE IF NOT EXISTS wordOccurrences";
        statement = conn.createStatement();
        statement.executeUpdate(sql);


    }

    /**
     * to force close a connection
     */
    public void shutdown() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Will create a table "word" if it doesn't exist.
     * @throws Exception
     */
    public void createTables() throws Exception {
        conn = openConnection();
        Statement statement;
        try {

            String sql = "CREATE TABLE IF NOT EXISTS word"+"(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " word VARCHAR(255), " +"frequency INTEGER(5),"+
                    " PRIMARY KEY ( id ))";
            statement = conn.createStatement();
            statement.executeUpdate(sql);
            System.out.println("using table 'word'");

        }catch( SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *  Will clear the table "word" if it exist.
     * @throws Exception
     */
    public void clearTables() throws Exception {
        conn = openConnection();
        Statement statement;
        try {
            String sql = "TRUNCATE TABLE word";
            statement = conn.createStatement();
            statement.executeUpdate(sql);
            System.out.println("WORD TABLE CLEANED");
        }catch( SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Will try to open a connection.
     * @return
     */
    private Connection openConnection() {
        Statement statement;

        if (conn != null)
            shutdown();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
            String sql = "CREATE DATABASE IF NOT EXISTS wordOccurrences";
            statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (ClassNotFoundException e) {
            System.out.println("Could not load the JDBC driver:"+e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not connect to DB"+e.getMessage());


        }
        return conn;

    }

    /**
     * To be used to manually insert word to the database
     * @param word
     * @param frequency
     */
    public void insertWord(String word,int frequency) {
        // for insert a new candidate
        conn = openConnection();

        String sql = "INSERT INTO word(word,frequency) "
                + "VALUES(?,?)";

        try (
             PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {

            // set parameters for statement
            pstmt.setString(1, word);
            pstmt.setInt(2, ++frequency);
            pstmt.executeUpdate();


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }

        }

    }

    /**
     * Will insert a new record, or update the frequency if the record exist.
     * @param myword
     */
    public void updateWord(String myword) {
        // for insert a new candidate
        conn = openConnection();

        String sql = "UPDATE word SET frequency = frequency +1 WHERE word='"+myword+"'";

        try (
                Statement stmt = conn.createStatement()) {

            // set parameters for statement

            stmt.executeUpdate(sql);


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }

        }

    }

    /**Check if a record exist
     *
     * @param myword
     * @return
     */
    public Boolean checkWord(String myword) {
        // for insert a new candidate
        conn = openConnection();
        boolean check=false;

        String sql = "SELECT * from word WHERE word='"+myword+"'";

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                System.out.printf("Word %s is in database\n", myword);
                check = true;
            }
            else
                System.out.printf("Word %s is not in database\n", myword);


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }

        }
return check;
    }

    /**
     * Will try to get all records from the database
     * @return
     * @throws SQLException
     */
    public List<Word> getWordList() throws SQLException {
        conn = openConnection();

        try (
                Statement stmnt = conn.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from word ");

        ){
            return getWords(rs);

        }
    }

    private List<Word> getWords(ResultSet rs) throws SQLException {


        List<Word> wordList = new ArrayList<>();
        while (rs.next()) {
            String Word = rs.getString("word");
            Integer Frequency = rs.getInt("frequency");

            Word word = new Word(Word, Frequency);
            wordList.add(word);
        }
        return wordList ;
    }

    /**
     * Will try to get a selected record from the database
     * @param myword
     * @return
     * @throws SQLException
     */
    public List<Word> selectWordList(String myword) throws SQLException {
        conn = openConnection();
        try (
                Statement stmnt = conn.createStatement();
                ResultSet rs = stmnt.executeQuery("SELECT * from word WHERE word='"+myword+"'");
        ){
            return getWords(rs);
        }
    }

    /** check if the table is empty
     *
     * @return
     */
    public boolean filldata() {
        // for insert a new candidate
        boolean check = false;
        conn = openConnection();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from word");
            if (rs.next() == false) {
                check = false;
            } else {
                check =true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdown();
            return check;
        }
    }


    public static void main(String[] args) throws Exception {
        jdbc obj = new jdbc();
        obj.createTables();
        // insert a new candidate
        Map<String, Integer> wordsMap = new HashMap<>();


        for (Map.Entry<String, Integer> entry : wordsMap.entrySet()) {
            obj.insertWord(entry.getKey(), entry.getValue());
            System.out.println(String.format("A word %s has been inserted.",entry.getKey() ));
        }
        obj.selectWordList("raven");

    }

}