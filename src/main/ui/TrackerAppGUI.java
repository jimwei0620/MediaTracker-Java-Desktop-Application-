package ui;

import exceptions.ItemNotFoundException;
import model.*;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;

// Main Graphical Interface
public class TrackerAppGUI extends JFrame {

    private JList list;
    private ListManager listColl;
    private GridBagConstraints gbc;
    private JTextPane listTextPanel;
    private JButton createButton;
    private JButton deleteButton;
    private JButton viewButton;
    private JButton saveButton;


    private static final String LIST_FILE = "./data/listFile.json";
    private static final String TAG_FILE = "./data/tagFile.json";
    private static final String ITEM_FILE = "./data/userItemFile.json";

    // MODIFIES: this
    // EFFECTS: Initialize the layout set up the GUI. Also load info from files.
    public TrackerAppGUI() {
        super("Media Tracker");
        listColl = new ListManager();
        try {
            loadInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        initListPanel();
        initListDetailsPanel();
        initListButtonOptions();

    }


    // MODIFIES: this
    // EFFECTS: initialize buttons for operations
    private void initListButtonOptions() {
        createButton = new JButton();
        deleteButton = new JButton();
        viewButton = new JButton();
        saveButton = new JButton();
        createButton.setText("Add");
        deleteButton.setText("Delete");
        saveButton.setText("Save");
        viewButton.setText("View");
        saveButton.setPreferredSize(new Dimension(80, 25));
        createButton.setPreferredSize(new Dimension(80, 25));
        deleteButton.setPreferredSize(new Dimension(80, 25));
        viewButton.setPreferredSize(new Dimension(80, 25));
        addMainButtons(createButton, deleteButton, viewButton, saveButton);
        createButton.addActionListener(e -> new NewListGUI(listColl));
    }

    // MODIFIES: this
    // EFFECTS: add and align main buttons to JFrame
    private void addMainButtons(JButton createButton,
                                JButton deleteButton, JButton viewButton, JButton saveButton) {
        setGridBagDimensions(1, 1);
        setGridBagXY(1, 2);
        setGridBagInsets(20,5,0,0);
        add(createButton, gbc);
        setGridBagXY(2,2);
        add(deleteButton, gbc);
        setGridBagXY(1, 3);
        setGridBagInsets(10, 5, 0,0);
        add(viewButton, gbc);
        setGridBagXY(2,3);
        add(saveButton, gbc);
    }

    // MODIFIES: this
    // EFFECTS Processes Tag data from files
    private void processTagData(ArrayList<Tag> tagRead) {
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
    private void processListData(ArrayList<MediaList> listRead) {
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
    private void processUserItemData(ArrayList<ReadUserItem> userItemRead) {
        if (userItemRead != null) {
            ArrayList<UserMediaItem> userMediaItems = processAllItems(userItemRead);
            listColl.getAllUserMediaItems().addAll(userMediaItems);
            loadToLists(userMediaItems);
            loadToTags(userMediaItems);

        } else {
            System.out.println("There are no items!");
        }
    }

    // MODIFIES: this
    // EFFECTS Processes all user item
    private ArrayList<UserMediaItem> processAllItems(ArrayList<ReadUserItem> itemRead) {
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
    private void loadToLists(ArrayList<UserMediaItem> userMediaItems) {
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
    private void loadToTags(ArrayList<UserMediaItem> userMediaItems) {
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

    // EFFECTS: save state of listColl to LISTS_FILE. Modeled after TellerApp
    private void saveProgram() {
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
    private void loadInfo() throws IOException {
        ArrayList<ReadUserItem> userItemRead = Reader.readItemFile(ITEM_FILE);
        ArrayList<Tag> tagRead = Reader.readTagFile(TAG_FILE);
        ArrayList<MediaList> listRead = Reader.readListFile(LIST_FILE);
        processTagData(tagRead);
        processListData(listRead);
        processUserItemData(userItemRead);
    }

    // MODIFIES: this
    // EFFECTS: init text area for list details
    private void initListDetailsPanel() {
        listTextPanel = new JTextPane();
        setGridBagInsets(10,5,0,0);
        setGridBagXY(1,0);
        setGridBagDimensions(2,1);
        listTextPanel.setBackground(Color.white);
        listTextPanel.setPreferredSize(new Dimension(175, 150));
        listTextPanel.setText("Empty");
        listTextPanel.setEditable(false);
        add(listTextPanel, gbc);
    }

    // EFFECTS: sets the on change for when a list is clicked
    private void setListOnChange() {
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                MediaList mediaListSelected = (MediaList) list.getSelectedValue();
                try {
                    listTextPanel.setText("Name: " + mediaListSelected.getName()
                            + "\n" + "Created: " + mediaListSelected.getDate() + "\n"
                            + "# of Items: " + listColl.getListOfMedia(mediaListSelected).size());
                } catch (ItemNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: init panel area for lists
    private void initListPanel() {
        setGridBagXY(0,0);
        setGridBagDimensions(1, 5);
        Set<MediaList> mediaListSet = listColl.allActiveLists();
        list = new JList(castMediaListToVector(mediaListSet));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setPreferredSize(new Dimension(200, 400));
        JScrollPane listJScrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listJScrollPane.setPreferredSize(new Dimension(200, 350));
        add(listJScrollPane, gbc);
        setListOnChange();
    }

    // MODIFIES: this
    // EFFECTS: Convert Set to Vector (type MediaList)
    private Vector<MediaList> castMediaListToVector(Set<MediaList> mediaListSet) {
        ArrayList<MediaList> mediaList = new ArrayList<>();
        for (MediaList i: mediaListSet) {
            mediaList.add(i);
        }
        return new Vector<MediaList>(mediaList);
    }

    // MODIFIES: This
    // EFFECTS: set grid bag grid x and y
    private void setGridBagXY(int x, int y) {
        this.gbc.gridx = x;
        this.gbc.gridy = y;
    }


    // MODIFIES: This
    // EFFECTS: set grid bag dimensions x and y
    private void setGridBagDimensions(int x, int y) {
        this.gbc.gridwidth = x;
        this.gbc.gridheight = y;
    }


    // MODIFIES: This
    // EFFECTS: set grid bag insets
    private void setGridBagInsets(int top, int left, int bottom, int right) {
        gbc.insets = new Insets(top, left, bottom, right);
    }
}
