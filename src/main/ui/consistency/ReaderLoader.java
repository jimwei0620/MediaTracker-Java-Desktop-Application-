package ui.consistency;

import exceptions.ItemNotFoundException;
import model.*;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ReaderLoader {

    private static final String LIST_FILE = "./data/listFile.json";
    private static final String TAG_FILE = "./data/tagFile.json";
    private static final String ITEM_FILE = "./data/userItemFile.json";

    // EFFECTS: save state of listColl to LISTS_FILE. Modeled after TellerApp
    public static void saveProgram(ListManager listColl) {
        try {
            Writer writer = new Writer(new File(LIST_FILE), new File(TAG_FILE), new File(ITEM_FILE));
            writer.write(listColl);
            writer.close();
            System.out.println("All lists are saved to file " + LIST_FILE);
        } catch (IOException e) {
            System.out.println("Unable to save accounts to " + LIST_FILE);
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: load info from LIST_FILE. Catch NullPointerException when there are no info in LIST_FILE
    public static void loadInfo(ListManager listColl) throws IOException {
        ArrayList<ReadUserItem> userItemRead = Reader.readItemFile(ITEM_FILE);
        ArrayList<Tag> tagRead = Reader.readTagFile(TAG_FILE);
        ArrayList<MediaList> listRead = Reader.readListFile(LIST_FILE);
        processTagData(tagRead, listColl);
        processListData(listRead, listColl);
        processUserItemData(userItemRead, listColl);
    }


    // MODIFIES: this
    // EFFECTS Processes Tag data from files
    private static void processTagData(ArrayList<Tag> tagRead, ListManager listColl) {
        if (tagRead != null) {
            for (Tag tag: tagRead) {
                listColl.addNewTag(tag);
            }
        } else {
            System.out.println("There are no tags!");
        }

    }

    // MODIFIES: this
    // EFFECTS Processes List data from files
    private static void processListData(ArrayList<MediaList> listRead, ListManager listColl) {
        if (listRead != null) {
            for (MediaList list: listRead) {
                listColl.addNewList(list);
            }
        } else {
            System.out.println("There are no lists");
        }
    }

    // MODIFIES: this
    // EFFECTS Processes User Item data from files
    private static void processUserItemData(ArrayList<ReadUserItem> userItemRead, ListManager listColl) {
        if (userItemRead != null) {
            ArrayList<UserMediaItem> userMediaItems = processAllItems(userItemRead);
            listColl.getAllUserMediaItems().addAll(userMediaItems);
            loadToLists(userMediaItems, listColl);
            loadToTags(userMediaItems, listColl);

        } else {
            System.out.println("There are no items!");
        }
    }

    // MODIFIES: this
    // EFFECTS Processes all user item
    private static ArrayList<UserMediaItem> processAllItems(ArrayList<ReadUserItem> itemRead) {
        ArrayList<UserMediaItem> userMediaItemArrayList = new ArrayList<>();
        for (ReadUserItem item: itemRead) {
            UserMediaItem userMediaItem = new UserMediaItem(item.getTitle());
            try {
                userMediaItem.setItemInfo("Status", item.getStatus());
                userMediaItem.setItemInfo("UserRating", item.getUserRating());
                userMediaItem.setItemInfo("Type", item.getType());
                userMediaItem.setItemInfo("UserComments", item.getUserComments());
                userMediaItemArrayList.add(userMediaItem);
                userMediaItem.getMetaDataOfType("List").addAll(item.getMetaDataList());
                userMediaItem.getMetaDataOfType("Tag").addAll(item.getMetaDataTag());
            } catch (ItemNotFoundException e) {
                e.printStackTrace();
                System.out.println("Internal Error");
            }
        }
        return userMediaItemArrayList;
    }

    // MODIFIES: this
    // EFFECTS load userMediaItems to lists
    private static void loadToLists(ArrayList<UserMediaItem> userMediaItems, ListManager listColl) {
        for (MediaList list: listColl.allActiveLists()) {
            for (UserMediaItem item: userMediaItems) {
                try {
                    if (item.containMetaDataOf("List", list.getName())) {
                        try {
                            listColl.getListOfMedia(list).add(item);
                        } catch (ItemNotFoundException e) {
                            System.out.println("Internal Error");
                        }
                    }
                } catch (ItemNotFoundException e) {
                    System.out.println("Internal Error");
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS load userMediaItems to tags
    private static void loadToTags(ArrayList<UserMediaItem> userMediaItems, ListManager listColl) {
        for (Tag tag: listColl.getAllActiveTags()) {
            for (UserMediaItem item: userMediaItems) {
                try {
                    if (item.containMetaDataOf("List", tag.getTagName())) {
                        try {
                            listColl.getListOfMediaWithTag(tag).add(item);
                        } catch (ItemNotFoundException e) {
                            System.out.println("Internal Error");
                        }
                    }
                } catch (ItemNotFoundException e) {
                    System.out.println("Internal Error");
                }
            }
        }
    }

}
