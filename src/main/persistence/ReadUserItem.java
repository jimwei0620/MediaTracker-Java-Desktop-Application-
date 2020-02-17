package persistence;

import model.MetaData;

import java.util.ArrayList;

// Dummy class used to read in information about file
public class ReadUserItem {
    private ArrayList<MetaData> metaDataTag;
    private ArrayList<MetaData> metaDataList;
    private String title;
    private String type;
    private String userComments;
    private String userRating;
    private String status;

    //This will actually never be instantiated
    public ReadUserItem() {
        metaDataList = new ArrayList<>();
        metaDataTag = new ArrayList<>();
        title = "testTitle";
        type = "testType";
        userComments = "testUserComments";
        userRating = "testUserRating";
        status = "testStatus";
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUserComments() {
        return userComments;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<MetaData> getMetaDataList() {
        return metaDataList;
    }

    public ArrayList<MetaData> getMetaDataTag() {
        return metaDataTag;
    }

}
