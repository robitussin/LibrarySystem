package database;

import data.Book;
import data.Member;

import javax.sound.midi.SysexMessage;
import java.sql.*;
import java.sql.ResultSet;

public class DatabaseHandler {

    private static DatabaseHandler handler = null;
    private static Statement stmt = null;
    private static PreparedStatement pstatement = null;

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public static Connection getDBConnection()
    {
        Connection connection = null;
        String dburl = "jdbc:mysql://localhost/librarydb?useTimezone=true&serverTimezone=UTC";
        String dbName = "librarydb";
        String userName = "root";
        String password = "";

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            connection = DriverManager.getConnection(dburl, userName, password);

        } catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }


    public static boolean addBook(Book book) {

        try {

            pstatement = getDBConnection().prepareStatement("INSERT INTO `booklist` (ID, Title, Author, Publisher, Type, Category) VALUES (?,?,?,?,?,?)");
            pstatement.setString(1, book.getId());
            pstatement.setString(2, book.getTitle());
            pstatement.setString(3, book.getAuthor());
            pstatement.setString(4, book.getPublisher());
            pstatement.setString(5, book.getType());
            pstatement.setString(6, book.getCategory());
            return pstatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    public static boolean addMember(Member member) {

        try {

            pstatement = getDBConnection().prepareStatement("INSERT INTO `memberlist` (ID, Name, Email) VALUES (?,?,?)");
            pstatement.setString(1, member.getId());
            pstatement.setString(2, member.getName());
            pstatement.setString(3, member.getEmail());
            return pstatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }


    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = getDBConnection().createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }

    public static boolean issueBook(Book book, Member member) {

        if(!updateBookAvailability(book)){
            return false;
        }

        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO `issuedbooklist` (BookID, MemberID, DateIssued, ReturnDate) VALUES (?,?,?,?)");
            pstatement.setString(1, book.getId());
            pstatement.setString(2, member.getId());
            pstatement.setString(3, book.getDateOfIssue());
            pstatement.setString(4, book.getDateOfReturn());
            return pstatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    public static boolean receiveBook(Book book, Member member) {

        if(!updateBookAvailability(book)){
            return false;
        }

        if(!removeIssuedBookList(book, member)){
            return false;
        }

        try {
            pstatement = getDBConnection().prepareStatement("INSERT INTO `receivedbooklist` (BookID, MemberID, DateReturned) VALUES (?,?,?)");
            pstatement.setString(1, book.getId());
            pstatement.setString(2, member.getId());
            pstatement.setString(3, book.getDateOfReturn());
            return pstatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    public static boolean deleteBook(Book book) {
        try {
            String deleteStatement = "DELETE FROM booklist WHERE ID = ?";
            pstatement = getDBConnection().prepareStatement(deleteStatement);
            pstatement.setString(1, book.getId());
            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteMember(Member member) {
        try {
            String deleteStatement = "DELETE FROM memberlist WHERE ID = ?";
            pstatement = getDBConnection().prepareStatement(deleteStatement);
            pstatement.setString(1, member.getId());
            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateBookAvailability(Book book){

        try {
            String update = "UPDATE booklist SET IsAvailable=? WHERE ID=?";
            pstatement = getDBConnection().prepareStatement(update);
            pstatement.setString(1, book.getAvailability());
            pstatement.setString(2, book.getId());
            int res = pstatement.executeUpdate();
            return (res > 0);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean removeIssuedBookList(Book book, Member member){
        try {

            String deleteStatement = "DELETE FROM issuedbooklist WHERE BookID = ? AND MemberID = ?";
            pstatement = getDBConnection().prepareStatement(deleteStatement);
            pstatement.setString(1, book.getId());
            pstatement.setString(2, member.getId());
            int res = pstatement.executeUpdate();
            if (res > 0) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkIssuedBook(Book book){
        String query = "SELECT * FROM issuedbooklist WHERE BookID = '"+ book.getId() + "'";

        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkBookExists(Book book){
        String query = "SELECT * FROM booklist WHERE ID = '"+ book.getId() + "'";
        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();

        }
        return false;
    }

    public static boolean checkMemberExists(Member member){
        String query = "SELECT * FROM memberlist WHERE ID = '"+ member.getId() + "'";
        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
