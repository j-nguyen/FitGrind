package ca.stclaircollege.fitgrind.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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
    private static final int DB_VERSION = 13;

    // Follow suit with our db fitgrind name
    private static final String DB_NAME = "fitgrind.db";

    // table name
    private static final String WEIGHTLOG_TABLE_NAME = "weight_log";
    private static final String FOOD_TABLE_NAME = "food";
    private static final String FOODLOG_TABLE_NAME = "food_log";
    private static final String WORKOUTDAY_TABLE_NAME = "workout_day";
    private static final String WORKOUTROUTINE_TABLE_NAME = "workout_routine";
    private static final String EXERCISE_TABLE_NAME = "exercise";
    private static final String CARDIOLOG_TABLE_NAME = "cardio_log";
    private static final String STRENGTHLOG_TABLE_NAME = "strength_log";
    private static final String WORKOUT_TABLE_NAME = "workout";
    private static final String PROGRESS_TABLE_NAME = "progress";

    // put it in a hashmap key
    private static final HashMap<Integer, String> KEY_MAP = new HashMap<Integer, String>();

    // initialize for our static provider
    static {
        KEY_MAP.put(208, "calories");
        KEY_MAP.put(269, "sugar");
        KEY_MAP.put(204, "total_fat");
        KEY_MAP.put(205, "carbohydrate");
        KEY_MAP.put(606, "saturated_fat");
        KEY_MAP.put(605, "trans_fat");
        KEY_MAP.put(601, "cholesterol");
        KEY_MAP.put(307, "sodium");
        KEY_MAP.put(291, "fiber");
        KEY_MAP.put(203, "protein");
        KEY_MAP.put(320, "vitamin_a");
        KEY_MAP.put(401, "vitamin_c");
        KEY_MAP.put(301, "calcium");
        KEY_MAP.put(303, "iron");
        KEY_MAP.put(306, "potassium");
    }

    // create our table names
    private static final String CREATE_WEIGHTLOG_TABLE =
            "CREATE TABLE weight_log (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "weight FLOAT, " +
                "date DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')));";

    private static final String CREATE_FOOD_TABLE =
            "CREATE TABLE food (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT, " +
                "serving TEXT, " +
                "calories FLOAT, " +
                "sugar FLOAT, " +
                "total_fat FLOAT, " +
                "carbohydrate FLOAT, " +
                "saturated_fat FLOAT, " +
                "trans_fat FLOAT, " +
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
                "date DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')));";

    private static final String CREATE_PROGRESS_TABLE =
            "CREATE TABLE progress (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "resource TEXT, " +
                "date DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')));";

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
        db.execSQL("DROP TABLE IF EXISTS " + WEIGHTLOG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FOODLOG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PROGRESS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUTDAY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUTROUTINE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CARDIOLOG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + STRENGTHLOG_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_TABLE_NAME);
        // relaunch onCreate
        onCreate(db);
    }

    // now we wanna create our crud operations in here. We will need a ton

    /**
     * Inserts routine into sqlite db. Parameters include the routine object.
     * @param program
     */
    public void insertProgram(Program program) {
        // Create the writeable DB
        SQLiteDatabase db = getWritableDatabase();
        // Use contentvalues
        ContentValues values = new ContentValues();
        // put name and desc
        values.put("name", program.getName());
        values.put("description", program.getDescription());
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
    public long insertFood(Food food) {
        SQLiteDatabase db = getWritableDatabase();
        // Create the content values inside
        ContentValues values = new ContentValues();
        values.put("name", food.getName());
        values.put("serving", food.getServingSize());
        // we now have to iterate through an array to make sure
        for (int i=0; i < FoodAPI.MAX_NUTRIENTS; i++) {
            // reference nutrient obj
            Nutrient nutrient = food.getNutrients().get(i);
            // we can reference the map using a dictionary for accesss
            values.put(KEY_MAP.get(nutrient.getNutrientId()), nutrient.getValue());
        }
        // now finally insert from the values
        long id = db.insert(FOOD_TABLE_NAME, null, values);
        db.close();
        return id;
    }

    /**
     * Inserts from the food log
     * @param foodId
     */
    public long insertFoodLog(long foodId) {
        SQLiteDatabase db = getWritableDatabase();
        // create the content values
        ContentValues values = new ContentValues();
        values.put("food_id", foodId);
        // now insert
        long id = db.insert(FOODLOG_TABLE_NAME, null, values);
        db.close();
        return id;
    }

    /*
     * UPDATE METHODS
     */

    /**
     * Updates specific row
     * @param program
     */
    public void updateRoutine(Program program) {
        // create db
        SQLiteDatabase db = getWritableDatabase();
        // setup content values
        ContentValues values = new ContentValues();
        // update the values
        values.put("name", program.getName());
        values.put("description", program.getDescription());
        // update the db
        db.update(WORKOUTROUTINE_TABLE_NAME, values, "id = ?", new String[]{String.valueOf(program.getId())});
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
     * @return an ArrayList of Program
     */
    public ArrayList<Program> selectAllRoutine() {
        // get readable db
        SQLiteDatabase db = getReadableDatabase();
        // create results
        ArrayList<Program> results = new ArrayList<Program>();
        // CREATE A QUERY
        Cursor cursor = db.rawQuery("SELECT * FROM " + WORKOUTROUTINE_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            // go through a do-while
            do {
                // get the required string
                results.add(new Program(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
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
        Cursor cursor = db.rawQuery("SELECT * FROM exercise INNER JOIN cardio_log ON exercise.id = cardio_log.exercise_id", null);
        if (cursor.moveToFirst()) {
            do {
                // now we can get the info for cardio log
                workoutList.add(new Cardio(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getDouble(3)));
            } while (cursor.moveToNext());
        }
        // now we check for other log
        cursor = db.rawQuery("SELECT * FROM exercise INNER JOIN strength_log ON exercise.id = strength.exercise_id", null);
        if (cursor.moveToFirst()) {
            do {
                // add for strength log
                workoutList.add(new Strength(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getDouble(5)));
            } while (cursor.moveToNext());
        }
        db.close();
        return workoutList;
    }

    public ArrayList<FoodLog> selectCalorieLogWeek() {
        ArrayList<FoodLog> results = new ArrayList<FoodLog>();
        for (int i=0; i < 7; i++) results.add(selectCalorieLogAt(i));
        return results;
    }


    /**
     * Retrieves a log of food, from the past x days.
     * @return A 2d arraylist. We know the exact amount the size of the outer, which is 7 for 7 days.
     */
    public FoodLog selectCalorieLogAt(int day) {
        FoodLog foodLog = null;
        ArrayList<Food> foodList = new ArrayList<Food>();
        // get db
        SQLiteDatabase db = getReadableDatabase();
        // create the dates
        String now = getCurrDateMinus(day);
        String sql = "SELECT food_log.date, food.* FROM food_log INNER JOIN food ON food_log.food_id = food.id " +
                "WHERE food_log.date BETWEEN ? AND ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{now + " 00:00:00", now + "23:59:59"});
        // check
        if (cursor.moveToFirst()) {
            do {
                Food food = new Food(cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(0));
                // we'll be forced to iterate through the hash map to get the key values
                for (String key : KEY_MAP.values()) food.addNutrient(new Nutrient(key, cursor.getDouble(cursor.getColumnIndex(key))));
                foodList.add(food);
            } while (cursor.moveToNext());
            // return food log
            foodLog = new FoodLog(now, foodList);
        }
        db.close();
        // return null if nothing
        return foodLog;
    }

    /**
     * Selects the 10 recent food logs
     * @return An arrayList of food
     */
    public ArrayList<Food> selectRecentFoodLog() {
        ArrayList<Food> foodList = null;
        // start db
        SQLiteDatabase db = getReadableDatabase();
        // create sql statement
        String sql = "SELECT food_log.date, food.id, food.name, food.serving, food.calories FROM food_log INNER JOIN food ON food_log.food_id = food.id " +
                "ORDER BY food_log.date LIMIT 10;";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            foodList = new ArrayList<Food>();
            do {
                Food food = new Food(cursor.getLong(1), cursor.getString(2), cursor.getString(3), cursor.getString(0));
                // we'll be forced to iterate through the hash map to get the key values
                food.addNutrient(new Nutrient("calories", cursor.getDouble(cursor.getColumnIndex("calories"))));
                foodList.add(food);

            } while (cursor.moveToNext());
        }
        return foodList;
    }

    private String getCurrDateMinus(int day) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -day);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    // get last record for each category

    /**
     * Gets the last recorded weight log in the weight log table
     * @return String date value in ISO format
     */
    public String lastRecordedWeightLog() {
        String result = null;
        // set up db
        SQLiteDatabase db = getReadableDatabase();
        // set up query
        Cursor cursor = db.rawQuery("SELECT date FROM weight_LOG ORDER BY date DESC LIMIT 1;", null);
        if (cursor.moveToLast()) result = cursor.getString(0);
        // return result
        return result;
    }

    /**
     * Gets the last recorded calorie log from the table.
     * @return String date value
     */
    public String lastRecordedCalorieLog() {
        String result = null;
        // set up db
        SQLiteDatabase db = getReadableDatabase();
        // set up query
        Cursor cursor = db.rawQuery("SELECT strftime('%Y-%m-%d', date) FROM food_log ORDER by date DESC LIMIT 1;", null);
        if (cursor.moveToLast()) result = cursor.getString(0);
        return result;
    }
}
