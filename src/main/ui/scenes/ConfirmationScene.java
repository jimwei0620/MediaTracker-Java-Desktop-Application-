package ui.scenes;

import exceptions.ItemNotFoundException;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ListManager;
import model.MediaItem;
import model.MediaList;
import ui.consistency.ErrorTextHandler;
import ui.consistency.InfoUpdaterUI;

// implements confirmation for different scenes
public class ConfirmationScene extends Stage implements NewScene {
    private  ListManager listColl;
    private  Pane suspendedPane;
    private Button conFirmButton;
    private Button cancelButton;
    private Text errorText;
    private FlowPane flowPane;
    private Text confirmText;
    private Scene scene;
    private FlowPane buttonFlowPane;

    // MODIFIES: this
    // EFFECTS: set listColl, suspendedPane, and sets up the confirmation scene (buttons)
    public ConfirmationScene(ListManager listColl, Pane suspendedPane) {
        this.listColl = listColl;
        this.suspendedPane = suspendedPane;
        initializeSceneContent();
        initializeScene();
        addSceneContent();
    }

    // MODIFIES: this
    // EFFECTS: initializes contents/elements of the scene
    @Override
    public void initializeSceneContent() {
        conFirmButton = new Button("Confirm");
        cancelButton = new Button("Cancel");
        errorText = new Text();
        flowPane = new FlowPane(Orientation.VERTICAL);
        confirmText = new Text("Are you sure you want to perform this action?");
        buttonFlowPane = new FlowPane(Orientation.HORIZONTAL);
    }

    // MODIFIES: this
    // EFFECTS: add elements to the scene
    @Override
    public void addSceneContent() {
        suspendedPane.setDisable(true);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setColumnHalignment(HPos.CENTER);
        flowPane.setVgap(10);
        buttonFlowPane.setAlignment(Pos.CENTER);
        buttonFlowPane.setHgap(20);
        buttonFlowPane.setVgap(10);
        flowPane.getChildren().add(confirmText);
        flowPane.getChildren().add(buttonFlowPane);
        flowPane.getChildren().add(errorText);
        buttonFlowPane.getChildren().add(conFirmButton);
        buttonFlowPane.getChildren().add(cancelButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes the scene, setting the title and size
    @Override
    public void initializeScene() {
        scene = new Scene(flowPane, 400, 100);
        this.setTitle("Confirmation");
        this.setScene(scene);
        this.show();
        this.setOnCloseRequest(event -> suspendedPane.setDisable(false));
    }

    // MODIFIES: this
    // EFFECTS: handles action to delete a media list
    public void confirmDeleteList(MediaList mediaListSelected, ListView listsView) {
        conFirmButton.setOnAction(event -> {
            try {
                ErrorTextHandler.clearErrorText(errorText);
                listColl.removeList(mediaListSelected);
                InfoUpdaterUI.updateListInfo(listsView, listColl);
                this.close();
                suspendedPane.setDisable(false);
            } catch (ItemNotFoundException e) {
                ErrorTextHandler.internalError(errorText);
            }
        });

        cancelButton.setOnAction(event -> {
            this.close();
            suspendedPane.setDisable(false);
        });
    }

    // MODIFIES: this
    // EFFECTS: handles action to delete a media list
    public void confirmDeleteItem(MediaList mediaListSelected, ListView listsView, MediaItem mediaItemSelected) {
        conFirmButton.setOnAction(event -> {
            try {
                ErrorTextHandler.clearErrorText(errorText);
                listColl.deleteMediaItemFromList(mediaListSelected, mediaItemSelected);
                InfoUpdaterUI.updateItemListInfo(listsView, listColl, mediaListSelected);
                this.close();
                suspendedPane.setDisable(false);
            } catch (ItemNotFoundException e) {
                ErrorTextHandler.internalError(errorText);
            }
        });

        cancelButton.setOnAction(event -> {
            this.close();
            suspendedPane.setDisable(false);
        });
    }

}
