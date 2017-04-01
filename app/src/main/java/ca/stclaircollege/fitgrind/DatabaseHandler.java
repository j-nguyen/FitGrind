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
    private static final String CREATE_WEIGHTLOG_TABLE =
            "CREATE TABLE weight_log (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "WEIGHT FLOAT, " +
                "DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

    private static final String CREATE_FOOD_TABLE =
            "CREATE TABLE food (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT, " +
                "serving TEXT, " +
                "calories FLOAT, " +
                "sugar FLOAT, " +
                "total_fat FLOAT, " +
                "cholesterol FLOAT, " +
                "sodium FLOAT, " +
                "fiber FLOAT, " +
                "protein FLOAT, " +
                "vitamin_a FLOAT, " +
                "vitamin_c FLOAT, " +
                "calcium FLOAT, " +
                "iron FLOAT, " +
                "potassium FLOAT);";

    private static final String CREATE_FOODLOG_TABLE =
            "CREATE TABLE food_log (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "food_id INTEGER REFERENCES food(id), " +
                "DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

    private static final String CREATE_PROGRESS_TABLE =
            "CREATE TABLE progress (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "resource TEXT);";

    private static final String CREATE_IMAGE_TABLE =
            "CREATE TABLE image (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "image_id INTEGER REFERENCES progress(id), " +
                "DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

    private static final String CREATE_WORKOUTDAY_TABLE =
            "CREATE TABLE workout_day (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "day VARCHAR(9));";

    private static final String CREATE_WORKOUTSTR_TABLE =
            "CREATE TABLE workout_strength (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT, " +
                "set INTEGER, " +
                "rep INTEGER, " +
                "weight FLOAT);" +

    private static final String CREATE_WORKOUTCARDIO_TABLE =
            "CREATE TABLE workout_cardio (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT, " +
                "time FLOAT);";

    private static final String CREATE_WORKOUT_TABLE =
            "CREATE TABLE workout (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "strength_id INTEGER REFERENCES workout_strength(id), " +
                "cardio_id INTEGER REFERENCES workout_cardio(id), " +
                "day_id INTEGER REFERENCES workout_day(id));";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WEIGHTLOG_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);
        db.execSQL(CREATE_FOODLOG_TABLE);
        db.execSQL(CREATE_PROGRESS_TABLE);
        db.execSQL(CREATE_IMAGE_TABLE);
        db.execSQL(CREATE_WORKOUTDAY_TABLE);
        db.execSQL(CREATE_WORKOUTSTR_TABLE);
        db.execSQL(CREATE_WORKOUTCARDIO_TABLE);
        db.execSQL(CREATE_WORKOUT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop the table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_WEIGHTLOG_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_FOOD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_FOODLOG_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_PROGRESS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_IMAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_WORKOUTDAY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_WORKOUTSTR_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_WORKOUTCARDIO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_WORKOUT_TABLE);
        // relaunch onCreate
        onCreate(db);
    }

    // now we wanna create our crud operations in here. We will need a ton
    


}
