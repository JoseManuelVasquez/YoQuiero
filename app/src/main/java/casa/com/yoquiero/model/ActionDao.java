package casa.com.yoquiero.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Data Access Object, Action
 */
@Dao
public interface ActionDao {

    @Query("SELECT * FROM `action`")
    List<Action> getAll();

    @Insert
    void insertAction(Action action);

    @Delete
    void deleteAction(Action action);

    @Update
    void updateAction(Action action);
}
