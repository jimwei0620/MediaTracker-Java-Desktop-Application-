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
public class MainListScene extends Stage implements NewScene {

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
        initializeSceneContent();
        try {
            ReaderLoader.loadInfo(listColl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeScene();
        addSceneContent();
    }

    // MODIFIES: this
    // EFFECTS: initializes contents/elements of the scene
    @Override
    public void initializeSceneContent() {
        listColl = new ListManager();
        gridPane = new GridPane();
        createButton = new Button();
        viewButton = new Button();
        deleteButton = new Button();
        saveButton = new Button();
        listsView = new ListView<>();
        errorText = new Text();
        editListButton = new Button();
        listDetails = new TextArea();
    }

    // MODIFIES: this
    // EFFECTS: add elements to the scene
    @Override
    public void addSceneContent() {
        setObjectListView();
        setListDetailView();
        setButtonsView();
        setButtonListeners();
    }

    // MODIFIES: this
    // EFFECTS: initializes the scene, setting the title and size
    @Override
    public void initializeScene() {
        scene = new Scene(gridPane, 400, 300);
        this.setTitle("Simple application");
        this.setScene(scene);
        this.show();
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
        listDetails.setWrapText(true);
        listDetails.setEditable(false);
        listDetails.setPrefSize(150, 150);
        gridPane.add(listDetails, 1, 0, 1, 10);
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(5));
    }


    // MODIFIES: this
    // EFFECTS: sets up the four buttons
    private void setButtonsView() {
        createButton.setText("Create List");
        createButton.setPrefSize(110, 15);
        gridPane.add(createButton, 2, 0, 1, 1);
        deleteButton.setText("Delete List");
        deleteButton.setPrefSize(110, 15);
        gridPane.add(deleteButton, 2, 1, 1, 1);
        viewButton.setText("View List");
        viewButton.setPrefSize(110, 15);
        gridPane.add(viewButton, 2, 2, 1, 1);
        saveButton.setText("Save all");
        saveButton.setPrefSize(110, 15);
        gridPane.add(saveButton, 2, 3, 1, 1);
        editListButton.setText("Edit List");
        editListButton.setPrefSize(110, 15);
        gridPane.add(editListButton, 2, 4, 1, 1);
        gridPane.add(errorText, 2, 5, 1,1);
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
                EditListScene editListScene = new EditListScene(listColl, gridPane);
                editListScene.editListInfo(mediaList, listsView);
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
