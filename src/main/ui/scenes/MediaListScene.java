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

// Class that displays GUI for mediaItems in a list
public class MediaListScene implements NewScene {

    private Scene prevScene;
    private ListManager listColl;
    private TagManager tagColl;
    private ItemManager itemColl;
    private Stage stage;
    private MediaList mediaListSelected;
    private GridPane gridPane;
    private Button createButton;
    private Button editButton;
    private Button deleteButton;
    private Button saveButton;
    private ListView<MediaItem> listsView;
    private Scene scene;
    private TextArea listDetails;
    private Text errorText;
    private Button exitButton;

    // MODIFIES: this
    // EFFECTS: sets the stage, listColl, previous scene, and initializes GUI elements
    public MediaListScene(Stage stage, ListManager listColl, TagManager tagColl, ItemManager itemColl,
                          Scene prevScene, MediaList mediaListSelected) {
        this.listColl = listColl;
        this.tagColl = tagColl;
        this.itemColl = itemColl;
        this.prevScene = prevScene;
        this.stage = stage;
        this.mediaListSelected = mediaListSelected;
        initializeSceneContent();
        initializeScene();
        addSceneContent();
    }

    // MODIFIES: this
    // EFFECTS: initializes contents/elements of the scene
    @Override
    public void initializeSceneContent() {
        createButton = new Button();
        editButton = new Button();
        deleteButton = new Button();
        saveButton = new Button();
        listsView = new ListView<>();
        errorText = new Text();
        exitButton = new Button();
        gridPane = new GridPane();
    }

    // MODIFIES: this
    // EFFECTS: add elements to the scene
    @Override
    public void addSceneContent() {
        setObjectsListView();
        setListDetailView();
        setButtonsView();
        setButtonListeners();
    }

    // MODIFIES: this
    // EFFECTS: initializes the scene, setting the title and size
    @Override
    public void initializeScene() {
        scene = new Scene(gridPane, 400, 300);
        stage.setScene(scene);
    }

    // MODIFIES: this
    // EFFECTS: set the media items view
    private void setObjectsListView() {
        try {
            ObservableList<MediaItem> listItems =
                    FXCollections.observableArrayList(listColl.getListOfMedia(mediaListSelected));
            listsView.setItems(listItems);
        } catch (ItemNotFoundException e) {
            ErrorTextHandler.internalError(errorText);
        }
        listsView.setEditable(true);
        listsView.setPrefSize(140, scene.getHeight());
        gridPane.add(listsView, 0, 0, 1, 10);
        listsView.setOnMouseClicked(event -> {
            try {
                ErrorTextHandler.clearErrorText(errorText);
                MediaItem mediaClicked = listsView.getSelectionModel().getSelectedItem();
                try {
                    displayItemDetails(mediaClicked);
                } catch (ItemNotFoundException e) {
                    ErrorTextHandler.internalError(errorText);
                }
            } catch (NullPointerException e) {
                return;
            }
        });
    }

    // EFFECTS: displays details of an item into listDetails (text area)
    private void displayItemDetails(MediaItem mediaClicked) throws ItemNotFoundException {
        listDetails.setText("Name: " + mediaClicked.getItemInfo("Title")
                + "\nRating: " + mediaClicked.getItemInfo("UserRating") + "/10"
                + "\nStatus: " + mediaClicked.getItemInfo("Status")
                + "\nType: " + mediaClicked.getItemInfo("Type")
                + "\nComments: " + mediaClicked.getItemInfo("UserComments"));
    }

    // MODIFIES: this
    // EFFECTS: sets up list details view
    private  void setListDetailView() {
        listDetails = new TextArea();
        listDetails.setWrapText(true);
        listDetails.setEditable(false);
        listDetails.setPrefSize(130, 130);
        gridPane.add(listDetails, 1, 0, 1, 10);
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(5));
    }

    // MODIFIES: this
    // EFFECTS: sets up the four buttons
    private void setButtonsView() {
        createButton.setText("Create Item");
        createButton.setPrefSize(110, 15);
        gridPane.add(createButton, 2, 0, 1, 1);
        deleteButton.setText("Delete Item");
        deleteButton.setPrefSize(110, 15);
        gridPane.add(deleteButton, 2, 2, 1, 1);
        editButton.setText("Edit Item");
        editButton.setPrefSize(110, 15);
        gridPane.add(editButton, 2, 3, 1, 1);
        saveButton.setText("Save all");
        saveButton.setPrefSize(110, 15);
        gridPane.add(saveButton, 2, 4, 1, 1);
        exitButton.setText("Exit List");
        exitButton.setPrefSize(110, 15);
        gridPane.add(exitButton, 2, 5, 1, 1);
        gridPane.add(errorText, 2, 6, 1,1);
    }

    // MODIFIES: this
    // EFFECTS: sets up button interactions
    private void setButtonListeners() {
        createButton.setOnAction(event -> createNewItem());
        editButton.setOnAction(event -> {
            try {
                ErrorTextHandler.clearErrorText(errorText);
                MediaItem mediaItem = listsView.getSelectionModel().getSelectedItem();
                if (mediaItem.equals(null)) {
                    throw new NullPointerException();
                }
                EditMediaScene editItemScene = new EditMediaScene(listColl, gridPane);
                editItemScene.editItemInfo(mediaItem, listsView, listDetails);
            } catch (NullPointerException e) {
                ErrorTextHandler.nothingSelectedError(errorText);
            }
        });
        saveButton.setOnAction(event -> ReaderLoader.saveProgram(listColl, tagColl, itemColl));
        deleteButton.setOnAction(event -> deleteList());
        exitButton.setOnAction(event -> stage.setScene(prevScene));
    }

    // MODIFIES: this
    // EFFECTS: sets up gui and interactions in order to delete a list
    private void deleteList() {
        try {
            ErrorTextHandler.clearErrorText(errorText);
            MediaItem mediaItem = listsView.getSelectionModel().getSelectedItem();
            if (mediaItem.equals(null)) {
                throw new NullPointerException();
            }
            ConfirmationScene confirmationScene = new ConfirmationScene(listColl, gridPane);
            confirmationScene.confirmDeleteItem(mediaListSelected, listsView, mediaItem);
        } catch (NullPointerException e) {
            ErrorTextHandler.nothingSelectedError(errorText);
        }
    }

    // MODIFIES: this
    // EFFECTS: handles gui and scene changes in order to create new list
    private void createNewItem() {
        ErrorTextHandler.clearErrorText(errorText);
        gridPane.setDisable(true);
        NewItemScene newItemScene = new NewItemScene(listColl, gridPane);
        newItemScene.createNewItem(listsView, mediaListSelected);
    }


}
