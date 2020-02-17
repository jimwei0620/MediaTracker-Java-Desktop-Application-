package model;

import com.google.gson.JsonArray;
import exceptions.DataExistAlreadyException;
import exceptions.ItemNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

// Represents a abstract media(Movies, tv show...) with different information
public abstract class MediaItem {
    protected Map<String, String> itemDetails;
    protected ArrayList<MetaData> listData;
    protected ArrayList<MetaData> tagData;


    // MODIFIES: this
    // EFFECTS: initialize different types of metaData;
    MediaItem() {
        itemDetails = new HashMap<>();
        listData = new ArrayList<>();
        tagData = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: update the metaData of item with type and nameOfObject specified.
    public void updateData(String type, String nameOfObject) throws DataExistAlreadyException {
        MetaData newData = new MetaData(nameOfObject);
        addMetaDataOfType(type, newData);
    }

    // MODIFIES: this
    // EFFECTS: remove metaData with type and nameOfObject specified.
    public void removeData(String type, String nameOfObject) throws ItemNotFoundException {
        MetaData data = new MetaData(nameOfObject);
        removeMetaDataOfType(type, data);
    }

    // EFFECTS: return true if the MediaItem is in any lists at all.
    public Boolean isActive() {
        return listData.size() != 0;
    }

    // EFFECTS: return the list of metaData of the specified type, throw ItemNotFoundException if type not found
    public ArrayList<MetaData> getMetaDataOfType(String type) throws ItemNotFoundException {
        if (type.equals("List")) {
            return  listData;
        } else if (type.equals("Tag")) {
            return tagData;
        }
        throw new ItemNotFoundException();
    }

    // EFFECTS: return info about the item of the specified type, throw ItemNotFoundException if type not found
    public String getItemInfo(String typeOfInfo) throws ItemNotFoundException {
        if (itemDetails.containsKey(typeOfInfo)) {
            return itemDetails.get(typeOfInfo);
        }
        throw new ItemNotFoundException();
    }

    // EFFECTS: add info about the item of the specified type, throw ItemNotFoundException if type not found
    public void setItemInfo(String typeOfInfo, String info) throws ItemNotFoundException {
        if (itemDetails.containsKey(typeOfInfo)) {
            itemDetails.put(typeOfInfo, info);
            return;
        }
        throw new ItemNotFoundException();
    }

    // EFFECTS: return the number of metaData with type lists
    public int numOfListMetaDataTypes() {
        return listData.size();
    }

    // EFFECTS: return the number of metaData with type tags
    public int numOfTagMetaDataTypes() {
        return tagData.size();
    }

    // EFFECTS: return the number of keys for item details
    public int numOfItemDetails() {
        return this.itemDetails.keySet().size();
    }

    // MODIFIES: this
    // EFFECTS: remove data from the list of metaData of the specified type, throw ItemNotFoundException if not found
    private void removeMetaDataOfType(String type, MetaData newData) throws ItemNotFoundException {
        ArrayList<MetaData> metaDataList = new ArrayList<>();
        if (type.equals("List")) {
            metaDataList = listData;
        } else if (type.equals("Tag")) {
            metaDataList = tagData;
        }
        if (metaDataList.contains(newData)) {
            metaDataList.remove(newData);
            return;
        }
        throw new ItemNotFoundException();
    }

    // MODIFIES: this
    // EFFECTS: add newData to the list of metaData of the specified type, throw ItemNotFoundException if type not found
    private void addMetaDataOfType(String type, MetaData newData)
            throws DataExistAlreadyException {
        ArrayList<MetaData> metaDataList = new ArrayList<>();
        if (type.equals("List")) {
            metaDataList = listData;
        } else if (type.equals("Tag")) {
            metaDataList = tagData;
        }
        if (metaDataList.contains(newData)) {
            throw new DataExistAlreadyException();
        }
        metaDataList.add(newData);
    }

    // EFFECTS: return true if
    public Boolean containMetaDataOf(String type, String nameOfObject) {
        ArrayList<MetaData> metaDataList = new ArrayList<>();
        if (type.equals("List")) {
            metaDataList = listData;
        } else if (type.equals("Tag")) {
            metaDataList = tagData;
        }
        for (MetaData data: metaDataList) {
            if (data.getNameOfObject().equals(nameOfObject)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MediaItem mediaItem = (MediaItem) o;
        return itemDetails.equals(mediaItem.itemDetails)
                && listData.equals(mediaItem.listData)
                && tagData.equals(mediaItem.tagData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemDetails, listData, tagData);
    }

    // EFFECTS: save the information of the object into json
    public abstract JSONObject save();

}
