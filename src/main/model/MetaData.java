package model;


import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

//represents information about a MediaItem
public class MetaData {
    private String date; //date added
    private String nameOfObject; //name of Object added

    public MetaData(String nameOfObject) {
        Calendar calendar = Calendar.getInstance();
        this.date = calendar.getTime().toString();
        this.nameOfObject = nameOfObject;
    }

    public String getDate() {
        return this.date;
    }

    public String getNameOfObject() {
        return this.nameOfObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetaData metaData = (MetaData) o;
        return nameOfObject.equals(metaData.nameOfObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfObject);
    }

    // EFFECTS: save the information of the object into json
    public JSONObject save() {
        JSONObject dataObject = new JSONObject();
        dataObject.put("date", this.date);
        dataObject.put("nameOfObject", this.nameOfObject);
        return dataObject;
    }
}
