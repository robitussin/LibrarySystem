package ui.listBook;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListBookController implements Initializable {

    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private BorderPane rootPane;

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, String> idCol;

    @FXML
    private TableColumn<Book, String>  titleCol;

    @FXML
    private TableColumn<Book, String>  authorCol;

    @FXML
    private TableColumn<Book, String>  publisherCol;

    @FXML
    private TableColumn<Book, String>  availCol;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeCol();
        loadData();
    }

    private void initializeCol(){

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availCol.setCellValueFactory(new PropertyValueFactory<>("avail"));
    }

    @FXML
    private void exitButtonHandler(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void loadData(){

        list.clear();

        DatabaseHandler handler = DatabaseHandler.getInstance();

        String query = "SELECT * FROM booklist";
        ResultSet result = handler.execQuery(query);

        try {
            while (result.next())
            {
                String id = result.getString("ID");
                String title = result.getString("Title");
                String author = result.getString("Author");
                String publisher = result.getString("Publisher");
                Boolean isAvailable = result.getBoolean("IsAvailable");

                list.add(new Book(id, title, author, publisher, isAvailable));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        tableView.setItems(list);
    }


    public static class Book {

        private final SimpleStringProperty id;
        private final SimpleStringProperty title;
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleStringProperty avail;

        public Book(String id, String title, String author, String pub, Boolean isAvailable) {

            this.id = new SimpleStringProperty(id);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(pub);

            if(isAvailable) {
                this.avail = new SimpleStringProperty("Available");
            }
            else {
                this.avail = new SimpleStringProperty("Issued");
            }
        }

        public String getId() {
            return id.get();
        }

        public String getTitle() {
            return title.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public String getAvail() {
            return avail.get();
        }
    }

    @FXML
    public void refreshButtonHandler(ActionEvent event){
        initializeCol();
        loadData();
    }
}
