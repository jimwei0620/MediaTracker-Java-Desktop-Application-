package ui;


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

import java.util.ArrayList;

// Represent GUI to edit data
public class EditMediaScene extends Stage {

    private Scene scene;
    private Pane suspendedPane;
    private ListManager listColl;
    private Button conFirmButton;
    private Button cancelButton;
    private GridPane gridPane;

    // MODIFIES: this
    //EFFECTS: sets up the main stage for editing medias
    public EditMediaScene(ListManager listColl, Pane suspendedPane) {
        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        this.listColl = listColl;
        this.suspendedPane = suspendedPane;
        conFirmButton = new Button("Confirm");
        cancelButton = new Button("Cancel");
        suspendedPane.setDisable(true);

        this.setTitle("Edit Information");
        this.setOnCloseRequest(event -> suspendedPane.setDisable(false));
        this.show();
    }

    // MODIFIES: mediaItem, listView, listDetails
    // EFFECTS: handles operations and scene for editing mediaItem
    public void editItemInfo(MediaItem mediaItem, ListView listView, TextArea listDetails) {
        scene = new Scene(gridPane, 300, 400);
        this.setScene(scene);
        try {
            TextField title = new TextField(mediaItem.getItemInfo("Title"));
            final ComboBox<String> status = new ComboBox<>();
            final ComboBox<String> userRating = new ComboBox<>();
            final ComboBox<String> type = new ComboBox<>();
            setItemComboBoxState(mediaItem, status, userRating, type);
            TextArea userComments = new TextArea(mediaItem.getItemInfo("UserComments"));
            Text titleErrorText = new Text("");
            Text titleHintText = new Text("Title:");
            Text userRatingHintText = new Text("Rating:");
            Text statusHintText = new Text("Status:");
            Text typeHintText = new Text("Type:");
            Text userCommentsHintText = new Text("Comments:");
            setItemEditLayout(title, status, userRating, type, userComments, titleErrorText, titleHintText,
                    userRatingHintText, statusHintText, typeHintText, userCommentsHintText,
                    conFirmButton, cancelButton);
            setEditItemButtons(conFirmButton, cancelButton, mediaItem, title, status, userRating, type, userComments,
                    listView, titleErrorText);
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: set the states of combo boxes
    private void setItemComboBoxState(MediaItem mediaItem, ComboBox<String> status,
                                      ComboBox<String> userRating, ComboBox<String> type) throws ItemNotFoundException {
        userRating.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10"));
        status.setItems(FXCollections.observableArrayList("Watched", "Ongoing", "To Be Watched"));
        type.setItems(FXCollections.observableArrayList("Movie", "Drama", "Animation", "TV"));
        status.setValue(mediaItem.getItemInfo("Status"));
        userRating.setValue(mediaItem.getItemInfo("UserRating"));
        type.setValue(mediaItem.getItemInfo("Type"));

    }

    // MODIFIES: this
    // EFFECTS: set the edit layout of the scene
    private void setItemEditLayout(TextField title, ComboBox status, ComboBox userRating, ComboBox type,
                                   TextArea userComments, Text titleErrorText, Text titleHintText,
                                   Text userRatingHintText, Text statusHintText, Text typeHintText,
                                   Text userCommentsHintText, Button conFirmButton, Button cancelButton) {
        gridPane.add(titleHintText, 0, 0, 1, 1);
        gridPane.add(title, 1, 0, 5, 1);
        gridPane.add(titleErrorText, 1, 1, 4, 1);
        gridPane.add(statusHintText, 0, 2, 1, 1);
        gridPane.add(status, 1, 2, 1, 1);
        gridPane.add(userRatingHintText, 0, 3, 1, 1);
        gridPane.add(userRating, 1, 3, 1, 1);
        gridPane.add(typeHintText, 0, 4, 1, 1);
        gridPane.add(type, 1, 4, 1,1);
        gridPane.add(userCommentsHintText, 0, 5, 1, 1);
        gridPane.add(userComments, 0, 6, 5,4);
        gridPane.add(conFirmButton, 0, 10, 1, 1);
        gridPane.add(cancelButton, 4, 10, 1,1);
    }

    // MODIFIES: this
    // EFFECTS: set the effects of buttons for editing mediaItems
    private void setEditItemButtons(Button conFirmButton, Button cancelButton, MediaItem mediaItem, TextField title,
                                    ComboBox status, ComboBox userRating, ComboBox type, TextArea userComments,
                                    ListView listView, Text titleErrorText) {
        cancelButton.setOnAction(event -> {
            suspendedPane.setDisable(false);
            this.close();
        });
        conFirmButton.setOnAction(event -> {
            try {
                if (title.getText().equals("")) {
                    throw new EmptyStringException();
                }
                mediaItem.setItemInfo("Title", title.getText());
                mediaItem.setItemInfo("UserRating", getComboBoxString(userRating));
                mediaItem.setItemInfo("Status", getComboBoxString(status));
                mediaItem.setItemInfo("Type", getComboBoxString(type));
                mediaItem.setItemInfo("UserComments", userComments.getText());
                listView.refresh();
                this.close();
                suspendedPane.setDisable(false);

            } catch (ItemNotFoundException e) {
                e.printStackTrace();
            } catch (EmptyStringException e) {
                ErrorTextHandler.emptyNameError(titleErrorText);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: get values from type ComboBox, if NullPointerException, return empty String
    private String getComboBoxString(ComboBox box) {
        try {
            return box.getValue().toString();
        } catch (NullPointerException e) {
            return "";
        }
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
