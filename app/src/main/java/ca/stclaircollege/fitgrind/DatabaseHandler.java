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
    private static final String DB_NAME = "fitgrind.db";

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

    // now finally, we can create our tables
    private static final String CREATE_TIMELOG_TABLE = "CREATE TABLE " + TIMELOG_TABLE_NAME + "(" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_DATE + " DATETIME);";
    private static final String CREATE_WEIGHTLOG_TABLE = "CREATE TABLE " + WEIGHTLOG_TABLE_NAME + "(" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_TIMEID + " INTEGER REFERENCES " +
            KEY_TIMEID + "(" + KEY_ID + "), " + KEY_WEIGHT + " FLOAT);";
    private static final String CREATE_CALORIELOG_TABLE = "CREATE TABLE " + CALORIELOG_TABLE_NAME + "(" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_TIMEID + " INTEGER REFERENCES " + KEY_TIMEID +
            "(" + KEY_ID + "), " + KEY_FOOD + " TEXT NOT NULL, " + KEY_CALORIES + " INTEGER NOT NULL);";
    private static final String CREATE_PROGRESS_TABLE = "CREATE TABLE " + PROGRESS_TABLE_NAME + "(" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_RESOURCE + " TEXT NOT NULL);";
    private static final String CREATE_IMAGELOCATION_TABLE = "CREATE TABLE " + IMAGELOCATION_TABLE_NAME + "(" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + KEY_IMAGEID +  " INTEGER REFERENCES " + PROGRESS_TABLE_NAME + "(" + KEY_ID + "), " +
            KEY_TIMEID + " INTEGER REFERENCES " + TIMELOG_TABLE_NAME + "(" + KEY_ID + "));";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TIMELOG_TABLE);
        db.execSQL(CREATE_WEIGHTLOG_TABLE);
        db.execSQL(CREATE_CALORIELOG_TABLE);
        db.execSQL(CREATE_PROGRESS_TABLE);
        db.execSQL(CREATE_IMAGELOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop the table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + IMAGELOCATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROGRESS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CALORIELOG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WEIGHTLOG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TIMELOG_TABLE_NAME);
    }

    // now we wanna create our crud operations in here. We will need a ton




}
