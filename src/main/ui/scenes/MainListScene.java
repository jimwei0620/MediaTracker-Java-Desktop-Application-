package ui.scenes;

import exceptions.ItemNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;
import ui.consistency.ErrorTextHandler;
import ui.consistency.ReaderLoader;

import java.io.IOException;

// Main Graphical Interface
public class MainListScene extends Stage {

    private ListManager listColl;
    private GridPane gridPane;
    private Button createButton;
    private Button viewButton;
    private Button deleteButton;
    private Button saveButton;
    private Button editListButton;
    private ListView<MediaList> listsView;
    private Scene scene;
    private TextArea listDetails;
    private Text errorText;

    // MODIFIES: this
    // EFFECTS: set the scene and all interactions
    public MainListScene() {
        listColl = new ListManager();
        gridPane = new GridPane();
        createButton = new Button();
        viewButton = new Button();
        deleteButton = new Button();
        saveButton = new Button();
        listsView = new ListView<>();
        errorText = new Text();
        editListButton = new Button();

        try {
            ReaderLoader.loadInfo(listColl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(gridPane, 300, 300);
        this.setTitle("Simple application");
        this.setScene(scene);
        this.show();
        createSceneContent();
    }

    // MODIFIES: this
    // EFFECTS: sets up the panel for the menu of application
    private void createSceneContent() {
        setObjectListView();
        setListDetailView();
        setButtonsView();
        setButtonListeners();
    }

    // MODIFIES: this
    // EFFECTS: sets up the list view
    private void setObjectListView() {
        ObservableList<MediaList> listItems = FXCollections.observableArrayList(listColl.allActiveLists());
        listsView.setItems(listItems);
        listsView.setEditable(true);
        listsView.setPrefSize(140, scene.getHeight());
        gridPane.add(listsView, 0, 0, 1, 10);
        listsView.setOnMouseClicked(event -> {
            try {
                ErrorTextHandler.clearErrorText(errorText);
                MediaList listClicked = listsView.getSelectionModel().getSelectedItem();
                try {
                    listDetails.setText("Name: " + listClicked.getName() + "\nDate: " + listClicked.getDate()
                            + "\n# of items: " + listColl.getListOfMedia(listClicked).size());

                } catch (ItemNotFoundException e) {
                    ErrorTextHandler.internalError(errorText);
                }
            } catch (NullPointerException e) {
                return;
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sets up list details view
    private  void setListDetailView() {
        listDetails = new TextArea();
        listDetails.setWrapText(true);
        listDetails.setEditable(false);
        listDetails.setPrefSize(130, 130);
        gridPane.add(listDetails, 1, 0, 4, 1);
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(5));
    }


    // MODIFIES: this
    // EFFECTS: sets up the four buttons
    private void setButtonsView() {
        createButton.setText("Create List");
        createButton.setPrefSize(110, 15);
        gridPane.add(createButton, 3, 5, 1, 1);
        deleteButton.setText("Delete List");
        deleteButton.setPrefSize(110, 15);
        gridPane.add(deleteButton, 3, 6, 1, 1);
        viewButton.setText("View List");
        viewButton.setPrefSize(110, 15);
        gridPane.add(viewButton, 3, 7, 1, 1);
        saveButton.setText("Save all");
        saveButton.setPrefSize(110, 15);
        gridPane.add(saveButton, 3, 8, 1, 1);
        editListButton.setText("Edit List");
        editListButton.setPrefSize(110, 15);
        gridPane.add(editListButton, 3, 9, 1, 1);
        gridPane.add(errorText, 3, 10, 1,1);
    }

    // MODIFIES: this
    // EFFECTS: sets up button interactions
    private void setButtonListeners() {
        createButton.setOnAction(event -> createNewList());
        viewButton.setOnAction(event -> {
            try {
                MediaList mediaList = itemSelectedInView();
                new MediaListScene(this, listColl, scene, mediaList);
            } catch (NullPointerException e) {
                ErrorTextHandler.nothingSelectedError(errorText);
            }
        });
        saveButton.setOnAction(event -> ReaderLoader.saveProgram(listColl));
        deleteButton.setOnAction(event -> deleteList());
        editListButton.setOnAction(event -> {
            try {
                MediaList mediaList = itemSelectedInView();
                EditMediaScene editMediaScene = new EditMediaScene(listColl, gridPane);
                editMediaScene.editListInfo(mediaList, listsView);
            } catch (NullPointerException e) {
                ErrorTextHandler.nothingSelectedError(errorText);
            }
        });
    }

    // EFFECTS: check if any mediaItem are selected, if not catches NullPointerException, else return the item
    private MediaList itemSelectedInView() throws  NullPointerException {
        MediaList mediaList;
        ErrorTextHandler.clearErrorText(errorText);
        mediaList = listsView.getSelectionModel().getSelectedItem();
        if (mediaList.equals(null)) {
            throw new NullPointerException();
        }
        return mediaList;
    }

    // MODIFIES: this
    // EFFECTS: sets up gui and interactions in order to delete a list
    private void deleteList() {
        try {
            ErrorTextHandler.clearErrorText(errorText);
            MediaList mediaList = listsView.getSelectionModel().getSelectedItem();
            if (mediaList.equals(null)) {
                throw new NullPointerException();
            }
            ConfirmationScene confirmationScene = new ConfirmationScene(listColl, gridPane);
            confirmationScene.confirmDeleteList(mediaList, listsView);
        } catch (NullPointerException e) {
            ErrorTextHandler.nothingSelectedError(errorText);
        }
    }

    // MODIFIES: this
    // EFFECTS: handles gui and scene changes in order to create new list
    private void createNewList() {
        ErrorTextHandler.clearErrorText(errorText);
        gridPane.setDisable(true);
        NewItemScene newItemScene = new NewItemScene(listColl, gridPane);
        newItemScene.createNewList(listsView);
    }



}
