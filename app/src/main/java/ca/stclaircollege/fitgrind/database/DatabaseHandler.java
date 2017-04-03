package ca.stclaircollege.fitgrind.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import ca.stclaircollege.fitgrind.api.Food;
import ca.stclaircollege.fitgrind.api.FoodAPI;
import ca.stclaircollege.fitgrind.api.Nutrient;

/**
 * DatabaseClassHandler class.
 * This handles the process of CRUD operations in SQLite, as well as table and data creation.
 * This will help us in tracking their calorie log, food log and other things that we will need.
 * @author Johnny Nguyen
 * @version 1.0
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // database version. Any DB Schema updates will require an increment version
    private static final int DB_VERSION = 2;

    // Follow suit with our db fitgrind name
    private static final String DB_NAME = "fitgrind.db";

    // table name
    private static final String WORKOUTROUTINE_TABLE_NAME = "workout_routine";
    private static final String EXERCISE_TABLE_NAME = "exercise";
    private static final String CARDIOLOG_TABLE_NAME = "cardio_log";
    private static final String STRENGTHLOG_TABLE_NAME = "strength_log";
    private static final String WORKOUT_TABLE_NAME = "workout";
    private static final String IMAGE_TABLE_NAME = "image";
    private static final String PROGRESS_TABLE_NAME = "progress";

    // put it in a key array for easier access
    private static final String[] FOOD_KEYS = new String[]{"calories", "sugar", "total_fat", "cholestrol", "sodium", "fiber", "protein",
                                                           "vitamin_a", "vitamin_c", "calcium", "iron", "potassium"};

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

    private static final String CREATE_WORKOUTROUTINE_TABLE =
            "CREATE TABLE workout_routine (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name VARCHAR(100) NOT NULL, " +
                "description TEXT NOT NULL);";

    private static final String CREATE_EXERCISE_TABLE =
            "CREATE TABLE exercise (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name VARCHAR(100) NOT NULL);";

    private static final String CREATE_CARDIOLOG_TABLE =
            "CREATE TABLE cardio_log (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "exercise_id INTEGER REFERENCES exercise(id), " +
                "time FLOAT);";

    private static final String CREATE_STRENGTHLOG_TABLE =
            "CREATE TABLE strength_log (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "exercise_id INTEGER REFERENCES exercise(id), " +
                "sets INTEGER, " +
                "rep INTEGER, " +
                "weight FLOAT);";

    private static final String CREATE_WORKOUT_TABLE =
            "CREATE TABLE workout (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "routine_id INTEGER REFERENCES workout_routine(id), " +
                "exercise_id INTEGER REFERENCES exercise(id), " +
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
        db.execSQL(CREATE_WORKOUTROUTINE_TABLE);
        db.execSQL(CREATE_EXERCISE_TABLE);
        db.execSQL(CREATE_CARDIOLOG_TABLE);
        db.execSQL(CREATE_STRENGTHLOG_TABLE);
        db.execSQL(CREATE_WORKOUT_TABLE);
        // next we will want to pre-populate the data. This way we know for sure it's there
        db.execSQL("INSERT INTO workout_day(id, day) VALUES (null, 'Monday');");
        db.execSQL("INSERT INTO workout_day(id, day) VALUES (null, 'Tuesday');");
        db.execSQL("INSERT INTO workout_day(id, day) VALUES (null, 'Wednesday');");
        db.execSQL("INSERT INTO workout_day(id, day) VALUES (null, 'Thursday');");
        db.execSQL("INSERT INTO workout_day(id, day) VALUES (null, 'Friday');");
        db.execSQL("INSERT INTO workout_day(id, day) VALUES (null, 'Saturday');");
        db.execSQL("INSERT INTO workout_day(id, day) VALUES (null, 'Sunday');");
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
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_WORKOUTROUTINE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_EXERCISE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_CARDIOLOG_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_STRENGTHLOG_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_WORKOUT_TABLE);
        // relaunch onCreate
        onCreate(db);
    }

    // now we wanna create our crud operations in here. We will need a ton

    /**
     * Inserts routine into sqlite db. Parameters include the routine object.
     * @param routine
     */
    public void insertRoutine(Routine routine) {
        // Create the writeable DB
        SQLiteDatabase db = getWritableDatabase();
        // Use contentvalues
        ContentValues values = new ContentValues();
        // put name and desc
        values.put("name", routine.getName());
        values.put("description", routine.getDescription());
        db.insert(WORKOUTROUTINE_TABLE_NAME, null, values);
    }

    /**
     * Inserts a workout, with the cardio object
     * @param cardio
     */
    public void insertWorkout(Cardio cardio, long routineId, long dayId) {
        // writeable db
        SQLiteDatabase db = getWritableDatabase();
        // create content values
        ContentValues values = new ContentValues();
        // input the values
        values.put("name", cardio.getName());
        // after inserting we want the id
        long id = db.insert(EXERCISE_TABLE_NAME, null, values);
        // now we wanna insert on the row
        values.clear(); // clear
        values.put("exercise_id", id);
        values.put("time", cardio.getTime());
        db.insert(CARDIOLOG_TABLE_NAME, null, values);
        // now we finally want to insert the final workout
        values.clear();
        values.put("routine_id", routineId);
        values.put("exercise_id", id);
        values.put("day_id", dayId);
        db.insert(WORKOUT_TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Inserts your 'weight-log' picture weekly.
     * @param progress
     */
    public void insertProgress(Progress progress) {
        SQLiteDatabase db = getWritableDatabase();
        // Create the content values
        ContentValues values = new ContentValues();
        // input the values
        values.put("resource", progress.getResource());
        // insert the db
        db.insert(PROGRESS_TABLE_NAME, null, values);
        db.close();
    }

    public void insertImageLocation(long imageId) {
        // insert an image location
        SQLiteDatabase db = getWritableDatabase();
        // Create content values
        ContentValues values = new ContentValues();
        // input the values selected
        values.put("image_id", imageId);
        // now insert db
        db.insert(IMAGE_TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Inserts workout with strength object
     * @param strength
     */
    public void insertWorkout(Strength strength, long routineId, long dayId) {
        // create db
        SQLiteDatabase db = getWritableDatabase();
        // create the content values
        ContentValues values = new ContentValues();
        // input the name and insert.
        values.put("name", strength.getName());
        // insert and retrieve the id
        long id = db.insert(EXERCISE_TABLE_NAME, null, values);
        values.clear();
        // insert again
        values.put("exercise_id", id);
        values.put("set", strength.getSet());
        values.put("rep", strength.getReptitions());
        values.put("weight", strength.getWeight());
        db.insert(STRENGTHLOG_TABLE_NAME, null, values);
        // now we finally want to insert the final workout
        values.clear();
        values.put("routine_id", routineId);
        values.put("exercise_id", id);
        values.put("day_id", dayId);
        db.insert(WORKOUT_TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Inserts food into db. We need to adjust a few things for the food.
     * @param food
     * @return id value
     */
    public ContentValues insertFood(Food food) {
        SQLiteDatabase db = getWritableDatabase();
        // Create the content values inside
        ContentValues values = new ContentValues();
        values.put("name", food.getName());
        values.put("serving", food.getServingSize());
        // we now have to iterate through an array to make sure
        for (int i=0; i < FoodAPI.MAX_NUTRIENTS; i++) {
            // reference nutrient obj
            Nutrient nutrient = food.getNutrients().get(i);
            values.put(FOOD_KEYS[i], nutrient.getNutrient());
        }
        return values;
//        return -1;
    }

    /*
     * UPDATE METHODS
     */

    /**
     * Updates specific row
     * @param routine
     */
    public void updateRoutine(Routine routine) {
        // create db
        SQLiteDatabase db = getWritableDatabase();
        // setup content values
        ContentValues values = new ContentValues();
        // update the values
        values.put("name", routine.getName());
        values.put("description", routine.getDescription());
        // update the db
        db.update(WORKOUTROUTINE_TABLE_NAME, values, "id = ?", new String[]{String.valueOf(routine.getId())});
        db.close();
    }

    /**
     * Updates with the cardio table
     * @param cardio
     * @param exerciseId
     */
    public void updateWorkout(Cardio cardio, long exerciseId) {
        // writeable db
        SQLiteDatabase db = getWritableDatabase();
        // create content values
        ContentValues values = new ContentValues();
        // input the values
        values.put("name", cardio.getName());
        // update db
        db.update(EXERCISE_TABLE_NAME, values, "id = ?", new String[]{String.valueOf(cardio.getId())});
        // now update on this workout
        values.clear(); // clear
        values.put("exercise_id", exerciseId);
        values.put("time", cardio.getTime());
        db.update(CARDIOLOG_TABLE_NAME, values, "exercise_id = ?", new String[]{String.valueOf(exerciseId)});
        db.close();
    }

    /**
     * Updates with the strength table
     * @param strength
     * @param exerciseId
     */
    public void updateWorkout(Strength strength, long exerciseId) {
        // create db
        SQLiteDatabase db = getWritableDatabase();
        // create the content values
        ContentValues values = new ContentValues();
        // input the name and insert.
        values.put("name", strength.getName());
        // insert and retrieve the id
        db.update(EXERCISE_TABLE_NAME, values, "id = ?", new String[]{String.valueOf(strength.getId())});
        // update on this field
        values.clear();
        values.put("exercise_id", exerciseId);
        values.put("set", strength.getSet());
        values.put("rep", strength.getReptitions());
        values.put("weight", strength.getWeight());
        db.update(STRENGTHLOG_TABLE_NAME, values, "exercise_id = ?", new String[]{String.valueOf(exerciseId)});
        db.close();
    }

    /**
     * delete routine
     * @param id
     */
    public void deleteRoutine(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(WORKOUTROUTINE_TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Deletes the cardio workout from 3 tables
     * @param id
     */
    public void deleteCardioWorkout(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CREATE_WORKOUT_TABLE, "exercise_id = ?", new String[]{String.valueOf(id)});
        db.delete(CARDIOLOG_TABLE_NAME, "exercise_id = ?", new String[]{String.valueOf(id)});
        db.delete(EXERCISE_TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Deletes the strength/weights workout from 3 tables.
     * @param id
     */
    public void deleteStrengthWorkout(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CREATE_WORKOUT_TABLE, "exercise_id = ?", new String[]{String.valueOf(id)});
        db.delete(STRENGTHLOG_TABLE_NAME, "exercise_id = ?", new String[]{String.valueOf(id)});
        db.delete(EXERCISE_TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Deletes the picture
     * @param id
     */
    public void deleteProgress(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(PROGRESS_TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * Gets all the routine available that the person made.
     * @return an ArrayList of Routine
     */
    public ArrayList<Routine> selectAllRoutine() {
        // get readable db
        SQLiteDatabase db = getReadableDatabase();
        // create results
        ArrayList<Routine> results = null;
        // CREATE A QUERY
        Cursor cursor = db.rawQuery("SELECT * FROM " + WORKOUTROUTINE_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            results = new ArrayList<Routine>();
            // go through a do-while
            do {
                // get the required string
                results.add(new Routine(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        db.close();
        // return specified results
        return results;
    }

    /**
     * Method to retrieve all of the workout.
     * @return An abstract list of all the workouts. You will need to use polymorphism to find it out
     */
    public ArrayList<WorkoutType> selectAllWorkout() {
        // to find out which one to return, we will use an abstract class in which that it relates to both
        // get db
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<WorkoutType> workoutList = new ArrayList<WorkoutType>();
        // check for cardio log
        Cursor cursor = db.rawQuery("SELECT * FROM exercise a INNER JOIN cardio_log b ON a.id = b.exercise_id", null);
        if (cursor.moveToFirst()) {
            do {
                // now we can get the info for cardio log
                workoutList.add(new Cardio(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getDouble(3)));
            } while (cursor.moveToNext());
        }
        // now we check for other log
        cursor = db.rawQuery("SELECT * FROM exercise a INNER JOIN strength_log b ON a.id = b.exercise_id", null);
        if (cursor.moveToFirst()) {
            do {
                // add for strength log
                workoutList.add(new Strength(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getDouble(5)));
            } while (cursor.moveToNext());
        }
        db.close();
        return workoutList;
    }

}
