package ui.consistency;

import exceptions.ItemNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import model.ListManager;
import model.MediaItem;
import model.MediaList;


// class used to update different views (GUI elements)
public class InfoUpdaterUI {

    // MODIFIES: listsView
    // EFFECTS: refreshes the listView for changes (MediaLists)
    public static void updateListInfo(ListView listsView, ListManager listColl) {
        ObservableList<MediaList> listItems = FXCollections.observableArrayList(listColl.allActiveLists());
        listsView.setItems(listItems);
    }

    // MODIFIES: listsView
    // EFFECTS: refreshes list view for mediaItems
    public static void updateItemListInfo(ListView listsView, ListManager listColl, MediaList mediaList) {
        ObservableList<MediaItem> listItems = null;
        try {
            listItems = FXCollections.observableArrayList(listColl.getListOfMedia(mediaList));
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
        }
        listsView.setItems(listItems);
    }

    // MODIFIES: textArea
    // EFFECTS: sets mediaItem details in a TextArea
    public static void updateItemDetails(TextArea textArea, MediaItem mediaItem) {

    }

}
