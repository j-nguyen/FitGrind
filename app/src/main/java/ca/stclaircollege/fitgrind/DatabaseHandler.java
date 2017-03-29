package ca.stclaircollege.fitgrind;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DatabaseClassHandler class.
 * This handles the process of CRUD operations in SQLite, as well as table and data creation.
 * This will help us in tracking their calorie log, food log and other things that we will need.
 * @author Johnny Nguyen
 * @version 1.0
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // database version. Any DB Schema updates will require an increment version
    private static final int DB_VERSION = 1;

    // Follow suit with our db fitgrind name
    private static final String DB_NAME = "fitgrind";

    // create our table names
    private static final String TIMELOG_TABLE_NAME = "time_log";
    private static final String WEIGHTLOG_TABLE_NAME = "weight_log";
    private static final String CALORIELOG_TABLE_NAME = "calorie_log";
    // progress table is where we keep track of our pictures.
    private static final String PROGRESS_TABLE_NAME = "progress";
    private static final String IMAGELOCATION_TABLE_NAME = "image_location";

    // Create our keys.

    private static final String KEY_ID = "id";
    private static final String KEY_TIMEID = "time_id";
    private static final String KEY_IMAGEID = "image_id";
    private static final String KEY_DATE = "date";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_FOOD = "food";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_RESOURCE = "resource";


    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
