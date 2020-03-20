package ui.scenes;


import exceptions.DataExistAlreadyException;
import exceptions.EmptyStringException;
import exceptions.ItemNotFoundException;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ListManager;
import model.MediaItem;
import model.MediaList;
import model.MetaData;
import ui.consistency.ErrorTextHandler;
import ui.consistency.InfoUpdaterUI;

import java.util.ArrayList;

// Represent GUI to edit data
public class EditListScene extends Stage implements NewScene {

    private Scene scene;
    private Pane suspendedPane;
    private ListManager listColl;
    private Button conFirmButton;
    private Button cancelButton;
    private GridPane gridPane;

    // MODIFIES: this
    //EFFECTS: sets up the main stage for editing medias
    public EditListScene(ListManager listColl, Pane suspendedPane) {
        initializeSceneContent();
        initializeScene();
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        this.listColl = listColl;
        this.suspendedPane = suspendedPane;
        suspendedPane.setDisable(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes contents/elements of the scene
    @Override
    public void initializeSceneContent() {
        gridPane = new GridPane();
        conFirmButton = new Button("Confirm");
        cancelButton = new Button("Cancel");
    }

    // MODIFIES: this
    // EFFECTS: add elements to the scene
    @Override
    public void addSceneContent() {

    }

    // MODIFIES: this
    // EFFECTS: initializes the scene, setting the title and size
    @Override
    public void initializeScene() {
        this.setTitle("Edit Information");
        this.setOnCloseRequest(event -> suspendedPane.setDisable(false));
        this.show();
    }

    // MODIFIES:this, listView
    // EFFECTS: edit list info
    public void editListInfo(MediaList mediaList, ListView listView) {
        Text errorText = new Text();
        scene = new Scene(gridPane, 200, 100);
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(conFirmButton);
        flowPane.getChildren().add(cancelButton);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(50);
        this.setScene(scene);
        Text listNameHintText = new Text("Name:");
        TextField listName = new TextField(mediaList.getName());
        gridPane.setHgap(5);
        gridPane.add(listNameHintText,0, 0, 1, 1);
        gridPane.add(listName, 1, 0, 3, 1);
        gridPane.add(errorText, 0, 1, 3, 1);
        gridPane.add(flowPane, 0, 2, 4, 1);
        flowPane.setPadding(new Insets(0, 0,5,0));
        setEditListButton(conFirmButton, cancelButton, listView, mediaList, listName, errorText);
    }

    // MODIFIES: this, listView
    // EFFECTS: handles button presses to edit list info
    private void setEditListButton(Button conFirmButton, Button cancelButton, ListView listView, MediaList mediaList,
                                   TextField listName, Text errorText) {
        cancelButton.setOnAction(event -> {
            this.close();
            suspendedPane.setDisable(false);
        });
        conFirmButton.setOnAction(event -> {
            try {
                ArrayList<MediaItem> mediaStoredInList = listColl.getListOfMedia(mediaList);
                MediaList newList = new MediaList(listName.getText());
                newList.setDate(mediaList.getDate());
                listColl.removeList(mediaList);
                listColl.addNewList(newList);
                convertMediaMetaData(mediaStoredInList, mediaList.getName(), newList.getName());
                listColl.addListOfItemToList(newList, mediaStoredInList);
                InfoUpdaterUI.updateListInfo(listView, listColl);
                this.close();
                suspendedPane.setDisable(false);
            } catch (EmptyStringException e) {
                ErrorTextHandler.emptyNameError(errorText);
            } catch (ItemNotFoundException | DataExistAlreadyException e) {
                e.printStackTrace();
                ErrorTextHandler.internalError(errorText);
            }
        });
    }

    // MODIFIES: mediaItems
    // EFFECTS: update list name for each metadata of each mediaItem
    private void convertMediaMetaData(ArrayList<MediaItem> mediaItems, String oldName, String newName) {
        for (MediaItem item: mediaItems) {
            try {
                ArrayList<MetaData> itemData = item.getMetaDataOfType("List");
                for (MetaData data: itemData) {
                    if (data.getNameOfObject().equals(oldName)) {
                        data.setNameOfObject(newName);
                    }
                }
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



}
