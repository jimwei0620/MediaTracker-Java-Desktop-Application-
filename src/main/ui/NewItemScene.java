package ui;

import exceptions.DataExistAlreadyException;
import exceptions.EmptyStringException;
import exceptions.ItemNotFoundException;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ListManager;
import model.MediaList;
import model.UserMediaItem;

import javax.management.openmbean.KeyAlreadyExistsException;


//Class used to handle new items like lists, media item... etc
public class NewItemScene extends Stage {
    private ListManager listColl;
    private Pane paneSuspended;
    private FlowPane flowPane;
    private Text errorText;
    private Text text;
    private Button addButton;
    private TextField userInput;

    // MODIFIES: This
    // EFFECTS: set this.listColl to listColl
    public NewItemScene(ListManager listColl, Pane paneSuspended) {
        this.listColl = listColl;
        this.paneSuspended = paneSuspended;
        flowPane = new FlowPane(Orientation.VERTICAL);
        errorText = new Text();
        text = new Text("");
        addButton = new Button();
        userInput = new TextField();
        setSceneForNewList(flowPane, errorText, text, addButton, userInput);
    }

    // MODIFIES: this
    // EFFECTS: handles  the operations for creating a new list
    public void createNewList(ListView listsView) {
        text.setText("Type the name of the list to create");
        addButton.setOnAction(event -> {
            try {
                ErrorTextHandler.clearErrorText(errorText);
                MediaList newList = new MediaList(userInput.getText());
                listColl.addNewList(newList);
                this.close();
                paneSuspended.setDisable(false);
                InfoUpdaterUI.updateListInfo(listsView, listColl);
            } catch (EmptyStringException e) {
                ErrorTextHandler.emptyNameError(errorText);
            } catch (KeyAlreadyExistsException e) {
                ErrorTextHandler.keyAlreadyExistsError("List", errorText);
            }
        });
    }



    // MODIFIES: this
    // EFFECTS: handles  the operations for creating a new item
    public void createNewItem(ListView listView, MediaList selectedList) {
        text.setText("Type the name of the Item to create");
        addButton.setOnAction(event -> {
            try {
                ErrorTextHandler.clearErrorText(errorText);
                UserMediaItem newItem = new UserMediaItem(userInput.getText());
                listColl.addMediaItemToList(selectedList, newItem);
                this.close();
                paneSuspended.setDisable(false);
                InfoUpdaterUI.updateItemListInfo(listView, listColl, selectedList);
            } catch (KeyAlreadyExistsException | DataExistAlreadyException e) {
                ErrorTextHandler.keyAlreadyExistsError("Item", errorText);
            } catch (ItemNotFoundException e) {
                ErrorTextHandler.internalError(errorText);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sets up the scene for new list
    private void setSceneForNewList(FlowPane flowPane,
                                    Text errorText, Text text, Button addButton, TextField userInput) {
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setVgap(10);
        flowPane.setColumnHalignment(HPos.CENTER);
        flowPane.getChildren().add(text);
        userInput.setPrefSize(150, 50);
        flowPane.getChildren().add(userInput);
        addButton.setText("Create");
        flowPane.getChildren().add(addButton);
        flowPane.getChildren().add(errorText);
        Scene scene = new Scene(flowPane, 300, 200);
        this.setTitle("New List");
        this.setScene(scene);
        this.show();
        this.setOnCloseRequest(event -> paneSuspended.setDisable(false));
    }



}
