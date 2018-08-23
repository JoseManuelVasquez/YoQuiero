package casa.com.yoquiero.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Action Table
 */

@Entity(tableName = "action")
public class Action {

    @PrimaryKey
    @NonNull
    private String action;

    @ColumnInfo(name = "url_image")
    private String urlImage;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "url_audio")
    private String urlAudio;

    /* ---------------- GETTERS AND SETTERS ---------------- */

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }
}
