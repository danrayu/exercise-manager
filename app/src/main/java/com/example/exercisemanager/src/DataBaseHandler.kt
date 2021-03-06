package com.example.exercisemanager.src

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.exercisemanager.R
import com.example.exercisemanager.ui.exercises.Exercise
import com.example.exercisemanager.ui.groups.Group
import com.example.exercisemanager.ui.home.Schedule
import com.example.exercisemanager.ui.muscles.Muscle
import org.threeten.bp.LocalDate

const val DATABASE_NAME = "EXERCISE_MANAGER"

//table containing exercises info
const val TABLE_EXERCISES = "Exercises"
const val COL_NAME_EXERCISES = "name"
const val COL_DESCRIPTION_EXERCISES = "description"
const val COL_ID_EXERCISES = "id"

//table containing muscle info
const val TABLE_MUSCLES = "Muscles"
const val COL_NAME_MUSCLES = "name"
const val COL_ID_MUSCLES = "id"

//table containing relation between exercise and muscle ids
const val TABLE_EXERCISE_MUSCLES = "ExerciseMuscles"
const val COL_EXERCISE_ID_EM = "exercise_id"
const val COL_MUSCLE_ID_EM = "muscle_id"


//table containing groups info
const val TABLE_GROUPS = "Groups"
const val COL_NAME_GROUPS = "Name"
const val COL_DESCRIPTION_GROUPS = "Description"
const val COL_ID_GROUPS = "id"

//table containing relation between group and exercise ids
const val TABLE_GROUP_EXERCISES = "GroupExercises"
const val COL_GROUP_ID_GE = "group_id"
const val COL_EXERCISE_ID_GE = "exercise_id"


// table containing information for group/exercise scheduling
const val TABLE_SCHEDULES = "RepeatableSchedules"
const val COL_SCHEDULE_ID = "schedule_id"
const val COL_SCHEDULE_PATTERN = "repeat_pattern"
const val COL_SCHEDULE_TYPE = "repeat_type"
// The date from which the pattern begins.
const val COL_REFERENCE_DATE = "reference_date"

// table containing displayableItems for schedules
const val TABLE_SCHEDULE_ITEMS = "ScheduleItems"
// schedule_id is also a column
const val COL_ELEMENT_ID = "element_id"
const val COL_IS_EXERCISE = "element_type"



class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,
    1) {
    val res: Resources = context.resources

    // general db methods
    override fun onCreate(db: SQLiteDatabase?) {
        createExerciseTable(db)
        createMusclesTable(db)
        createExerciseMusclesTable(db)
        createGroupsTable(db)
        createGroupExercisesTable(db)
        createRepeatableSchedulesTable(db)
        createScheduleItemsTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun resetDataBase() {
        val db = this.writableDatabase
        val dropEx = "DROP TABLE IF EXISTS '$TABLE_EXERCISES'"
        db?.execSQL(dropEx)
        val dropMs = "DROP TABLE IF EXISTS '$TABLE_MUSCLES'"
        db?.execSQL(dropMs)
        val dropExMs = "DROP TABLE IF EXISTS '$TABLE_EXERCISE_MUSCLES'"
        db?.execSQL(dropExMs)
        val dropGr = "DROP TABLE IF EXISTS '$TABLE_GROUPS'"
        db?.execSQL(dropGr)
        val dropGrExs = "DROP TABLE IF EXISTS '$TABLE_GROUP_EXERCISES'"
        db?.execSQL(dropGrExs)
        val dropSchRe = "DROP TABLE IF EXISTS '$TABLE_SCHEDULES'"
        db?.execSQL(dropSchRe)
        val dropSchIt = "DROP TABLE IF EXISTS '$TABLE_SCHEDULE_ITEMS'"
        db?.execSQL(dropSchIt)
        onCreate(db)
    }


    // create table methods
    private fun createExerciseTable(db: SQLiteDatabase?) {
        val createExerciseTable = "CREATE TABLE " + TABLE_EXERCISES +
                " (" + COL_ID_EXERCISES + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME_EXERCISES + " VARCHAR(255)," +
                COL_DESCRIPTION_EXERCISES + " VARCHAR(2047));"
        db?.execSQL(createExerciseTable)
    }

    private fun createMusclesTable(db: SQLiteDatabase?) {
        // creating table
        val createMusclesTable = "CREATE TABLE " + TABLE_MUSCLES + " (" + COL_ID_MUSCLES +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_NAME_MUSCLES + " VARCHAR(255));"
        db?.execSQL(createMusclesTable)

        // inserting default values
        val musclesDefaultValues = res.getStringArray(R.array.muscle_list)
        for (muscle in musclesDefaultValues) {
            if (db != null) {
                insertMusclesData(db, muscle)
            }
        }
    }

    private fun createExerciseMusclesTable(db: SQLiteDatabase?) {
        val sqlCommand = "CREATE TABLE " + TABLE_EXERCISE_MUSCLES +
                " (" + COL_EXERCISE_ID_EM + " INT, " + COL_MUSCLE_ID_EM + " INT, " +
                " FOREIGN KEY(" + COL_EXERCISE_ID_EM + ") REFERENCES " +
                TABLE_EXERCISES + "(" + COL_ID_EXERCISES + "), " +
                " FOREIGN KEY(" + COL_MUSCLE_ID_EM + ") REFERENCES " +
                TABLE_MUSCLES + "(" + COL_ID_MUSCLES + "));"
        db?.execSQL(sqlCommand)
    }

    private fun createGroupsTable(db: SQLiteDatabase?) {
        val createGroupsTable =
                "CREATE TABLE $TABLE_GROUPS " +
                "($COL_ID_GROUPS INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_NAME_GROUPS VARCHAR(255)," +
                "$COL_DESCRIPTION_GROUPS VARCHAR(2047));"
        db?.execSQL(createGroupsTable)
    }

    private fun createGroupExercisesTable(db: SQLiteDatabase?) {
        val sqlCommand = "CREATE TABLE " + TABLE_GROUP_EXERCISES +
                " (" + COL_GROUP_ID_GE + " INT, " + COL_EXERCISE_ID_GE + " INT, " +
                " FOREIGN KEY(" + COL_GROUP_ID_GE + ") REFERENCES " +
                TABLE_EXERCISES + "(" + COL_ID_GROUPS + "), " +
                " FOREIGN KEY(" + COL_EXERCISE_ID_GE + ") REFERENCES " +
                TABLE_MUSCLES + "(" + COL_ID_EXERCISES + "));"
        db?.execSQL(sqlCommand)
    }

    private fun createRepeatableSchedulesTable(db: SQLiteDatabase?) {
        val createSchedulesTable =
            "CREATE TABLE $TABLE_SCHEDULES " +
            "($COL_SCHEDULE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COL_SCHEDULE_PATTERN VARCHAR(255)," +
            "$COL_SCHEDULE_TYPE VARCHAR(255)," +
            "$COL_REFERENCE_DATE DATETIME);"
        db?.execSQL(createSchedulesTable)
    }

    private fun createScheduleItemsTable(db: SQLiteDatabase?) {
        val createSchedulesTable =
            "CREATE TABLE $TABLE_SCHEDULE_ITEMS " +
                    "($COL_SCHEDULE_ID INT," +
                    "$COL_ELEMENT_ID INT," +
                    "$COL_IS_EXERCISE NUMBER(1));"
        db?.execSQL(createSchedulesTable)
    }

    // insert methods
    fun insertExerciseData(db:SQLiteDatabase, exercise: Exercise) {
        val contentValues = ContentValues()
        contentValues.put(COL_NAME_EXERCISES, exercise.name)
        contentValues.put(COL_DESCRIPTION_EXERCISES, exercise.description)

        val resultE = db.insert(TABLE_EXERCISES, null, contentValues)
        if (resultE == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
        insertExerciseMuscleRelation(db, getExerciseFromName(db, exercise.name).id, exercise.muscles)
    }

    fun insertMusclesData(db:SQLiteDatabase, muscle: String) {
        val contentValues = ContentValues()
        contentValues.put(COL_NAME_MUSCLES, muscle)
        val result = db.insert(TABLE_MUSCLES, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertGroupData(db: SQLiteDatabase, group: Group) {
        val contentValues = ContentValues()
        contentValues.put(COL_NAME_GROUPS, group.name)
        contentValues.put(COL_DESCRIPTION_GROUPS, group.description)

        val resultE = db.insert(TABLE_GROUPS, null, contentValues)
        if (resultE == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
        insertGroupExerciseRelation(db, getGroupFromName(db, group.name).id, group.exercises)
    }

    private fun insertExerciseMuscleRelation(db: SQLiteDatabase?, exerciseId: Int, muscles: MutableList<Muscle>) : Boolean {
        val contentValues = ContentValues()
        for (muscle in muscles) {
            contentValues.put(COL_EXERCISE_ID_EM, exerciseId)
            contentValues.put(COL_MUSCLE_ID_EM, muscle.id)
            val result = db!!.insert(TABLE_EXERCISE_MUSCLES, null, contentValues)
            if (result == (0).toLong()) {
                return false
            }
        }
        return true
    }

    private fun insertGroupExerciseRelation(db: SQLiteDatabase?, groupId: Int, exercises: MutableList<Exercise>) : Boolean {
        val contentValues = ContentValues()
        for (exercise in exercises) {
            contentValues.put(COL_GROUP_ID_GE, groupId)
            contentValues.put(COL_EXERCISE_ID_GE, exercise.id)
            val result = db!!.insert(TABLE_GROUP_EXERCISES, null, contentValues)
            if (result == (0).toLong()) {
                return false
            }
        }
        return true
    }

    fun insertSchedule(db: SQLiteDatabase?, schedule: Schedule) {
        val contentValues = ContentValues()
        contentValues.put(COL_SCHEDULE_PATTERN, schedule.schedulePattern)
        contentValues.put(COL_SCHEDULE_TYPE, schedule.scheduleType)
        contentValues.put(COL_REFERENCE_DATE, schedule.referenceDate.toString())
        val result = db?.insert(TABLE_SCHEDULES, null, contentValues)
        insertScheduleItems(db, schedule)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertScheduleItems(
        db: SQLiteDatabase?, schedule: Schedule) {
        this.deleteScheduleItems(this.writableDatabase, schedule.id)
        for (item in schedule.displayableItems!!) {
            val contentValues = ContentValues()
            val schedules = readScheduleData(this.readableDatabase)
            contentValues.put(COL_SCHEDULE_ID, schedules[schedules.size - 1].id)
            contentValues.put(COL_ELEMENT_ID, item.id)
            contentValues.put(COL_IS_EXERCISE, item is Exercise)
            db?.insert(TABLE_SCHEDULE_ITEMS, null, contentValues)
        }
    }


    // update methods
    fun updateExerciseData(db:SQLiteDatabase, exercise: Exercise) {
        val editData = "UPDATE " + TABLE_EXERCISES + " SET " +
                COL_NAME_EXERCISES + " = " + "\'" + exercise.name + "\'" + "," +
                COL_DESCRIPTION_EXERCISES + " = " + "\'" + exercise.description + "\'" +
                " WHERE " + COL_ID_EXERCISES + " = " + exercise.id + ";"
        db.execSQL(editData)
        updateExerciseMusclesData(db, exercise)
    }

    fun updateMusclesData(db:SQLiteDatabase, muscle: Muscle) {
        val editData = "UPDATE " + TABLE_MUSCLES + " SET " + COL_NAME_MUSCLES + " = " +
                "\'" + muscle.name + "\' " +
                " WHERE " + COL_ID_MUSCLES + " = " + muscle.id + ";"
        db.execSQL(editData)
    }

    private fun updateExerciseMusclesData(db: SQLiteDatabase, exercise: Exercise) {
        val muscleList = exercise.muscles
        deleteExerciseMuscleData(db, exercise.id)
        insertExerciseMuscleRelation(db, exercise.id, muscleList)
    }

    fun updateGroupData(db: SQLiteDatabase, group: Group) {
        val editData = "UPDATE " + TABLE_GROUPS + " SET " +
                COL_NAME_GROUPS + " = " + "\'" + group.name + "\'" + "," +
                COL_DESCRIPTION_GROUPS + " = " + "\'" + group.description + "\'" +
                " WHERE " + COL_ID_GROUPS + " = " + group.id + ";"
        updateGroupExercisesData(db, group)
        db.execSQL(editData)
    }

    private fun updateGroupExercisesData(db: SQLiteDatabase, group: Group) {
        val exerciseList = group.exercises
        deleteGroupExercisesData(db, group.id)
        insertGroupExerciseRelation(db, group.id, exerciseList)
    }

    fun updateScheduleData(db: SQLiteDatabase, schedule: Schedule) {
        deleteScheduleData(db, schedule.id)
        insertSchedule(db, schedule)
    }


    // read methods
    fun readExercisesData(db:SQLiteDatabase): MutableList<Exercise> {
        val list: MutableList<Exercise> = ArrayList()
        val query = "Select * from $TABLE_EXERCISES"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val exerciseId = result.getInt(result.getColumnIndex(COL_ID_EXERCISES))
                val exercise = Exercise(
                    result.getString(result.getColumnIndex(COL_NAME_EXERCISES)),
                    result.getString(result.getColumnIndex(COL_DESCRIPTION_EXERCISES)),
                    readExerciseMuscleData(db, exerciseId),
                    id = exerciseId
                )
                list.add(exercise)
            }
            while (result.moveToNext())
        }
        result.close()
        return list
    }

    fun readMusclesData(db:SQLiteDatabase): MutableList<Muscle>  {
        val list: MutableList<Muscle> = ArrayList()
        val query = "Select * from $TABLE_MUSCLES"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val muscle = Muscle(
                    result.getString(result.getColumnIndex(COL_NAME_MUSCLES)),
                    id = result.getInt(result.getColumnIndex(COL_ID_MUSCLES))
                )
                list.add(muscle)
            }
            while (result.moveToNext())
        }
        result.close()
        return list
    }

    private fun readExerciseMuscleData(db: SQLiteDatabase, exerciseId: Int): MutableList<Muscle> {
        val list: MutableList<Muscle> = mutableListOf()
        val query = "Select $TABLE_EXERCISE_MUSCLES.$COL_EXERCISE_ID_EM, $TABLE_MUSCLES.$COL_NAME_MUSCLES, " +
                "$TABLE_MUSCLES.$COL_ID_MUSCLES FROM $TABLE_EXERCISE_MUSCLES INNER JOIN $TABLE_MUSCLES" +
                " ON $TABLE_EXERCISE_MUSCLES.$COL_MUSCLE_ID_EM = $TABLE_MUSCLES.$COL_ID_MUSCLES;"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val currentExerciseId = result.getInt(result.getColumnIndex(COL_EXERCISE_ID_EM))
                if (currentExerciseId == exerciseId) {
                    val muscle = Muscle(
                        result.getString(result.getColumnIndex(COL_NAME_MUSCLES)),
                        id = result.getInt(result.getColumnIndex(COL_ID_MUSCLES))
                    )
                    list.add(muscle)
                }
            }
            while (result.moveToNext())
        }
        result.close()
        return list
    }
    
    fun readGroupData(db: SQLiteDatabase) : MutableList<Group> {
        val list: MutableList<Group> = ArrayList()
        val query = "Select * from $TABLE_GROUPS"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val groupId = result.getInt(result.getColumnIndex(COL_ID_GROUPS))
                val group = Group(
                    id = groupId,
                    result.getString(result.getColumnIndex(COL_NAME_GROUPS)),
                    result.getString(result.getColumnIndex(COL_DESCRIPTION_GROUPS)),
                    readGroupExercisesData(db, groupId)
                )
                list.add(group)
            }
            while (result.moveToNext())
        }
        result.close()
        return list
    }
    
    private fun readGroupExercisesData(db: SQLiteDatabase, groupId: Int) : MutableList<Exercise> {
        val list: MutableList<Exercise> = mutableListOf()
        val query = "SELECT " +
                "$TABLE_GROUP_EXERCISES.$COL_GROUP_ID_GE, " +
                "$TABLE_EXERCISES.$COL_NAME_EXERCISES, " +
                "$TABLE_EXERCISES.$COL_DESCRIPTION_EXERCISES, " +
                "$TABLE_EXERCISES.$COL_ID_EXERCISES " +
                "FROM $TABLE_GROUP_EXERCISES " +
                "INNER JOIN $TABLE_EXERCISES " +
                "ON $TABLE_GROUP_EXERCISES.$COL_EXERCISE_ID_GE = $TABLE_EXERCISES.$COL_ID_EXERCISES;"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                if (result.getInt(result.getColumnIndex(COL_GROUP_ID_GE)) == groupId) {
                    val id = result.getInt(result.getColumnIndex(COL_ID_EXERCISES))
                    val exercise = Exercise(
                        result.getString(result.getColumnIndex(COL_NAME_EXERCISES)),
                        result.getString(result.getColumnIndex((COL_DESCRIPTION_EXERCISES))),
                        readExerciseMuscleData(db, id),
                        id = id
                    )
                    list.add(exercise)
                }
            }
            while (result.moveToNext())
        }
        result.close()
        return list
    }

    fun readScheduleData(db: SQLiteDatabase) : MutableList<Schedule> {
        val list: MutableList<Schedule> = ArrayList()
        val query = "Select * from $TABLE_SCHEDULES"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                list.add(Schedule(
                    result.getInt(result.getColumnIndex(COL_SCHEDULE_ID)),
                    result.getString(result.getColumnIndex(COL_SCHEDULE_PATTERN)),
                    readScheduleItems(db, result.getInt(result.getColumnIndex(COL_SCHEDULE_ID))),
                    result.getString(result.getColumnIndex(COL_SCHEDULE_TYPE)),
                    LocalDate.parse(result.getString(result.getColumnIndex(COL_REFERENCE_DATE)))))
                }
                while (result.moveToNext())
            }
        result.close()
        return list
    }

    fun readScheduleIds(db: SQLiteDatabase) : MutableList<Int> {
        val list: MutableList<Int> = ArrayList()
        val query = "Select * from $TABLE_SCHEDULES"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                list.add(result.getInt(result.getColumnIndex(COL_SCHEDULE_ID)))
            }
            while (result.moveToNext())
        }
        result.close()
        return list
    }

    private fun readScheduleItems(db: SQLiteDatabase, scheduleId: Int) : MutableList<DisplayableItem> {
        val list: MutableList<DisplayableItem> = ArrayList()
        val query = "Select * from $TABLE_SCHEDULE_ITEMS"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                if (result.getInt(result.getColumnIndex(COL_SCHEDULE_ID)) == scheduleId) {
                    if (result.getInt(result.getColumnIndex(COL_IS_EXERCISE)).toBool()) {
                        list.add(getExerciseFromId(
                            db, result.getInt(result.getColumnIndex(COL_ELEMENT_ID))))
                    } else {
                        list.add(getGroupFromId(
                            db, result.getInt(result.getColumnIndex(COL_ELEMENT_ID))))
                    }
                }
            }
            while (result.moveToNext())
        }
        result.close()
        return list
    }

    fun readScheduledItemsAtDate(db: DataBaseHandler, date: LocalDate) : MutableList<DisplayableItem> {
        val schedules = db.readScheduleData(db.readableDatabase)
        val itemList: MutableList<DisplayableItem> = ArrayList()
        for (schedule in schedules) {
            if (schedule.isScheduledAtDate(date)) {
                itemList.addAll(schedule.displayableItems!!.toTypedArray())
            }
        }
        return itemList
    }


    // delete methods
    fun deleteExerciseItem(db:SQLiteDatabase, exercise: Exercise) {
        val deleteEData = "DELETE FROM " + TABLE_EXERCISES + " WHERE " + COL_ID_EXERCISES + " = " +
                exercise.id + ";"
        deleteExerciseMuscleData(db, exercise.id)
        db.execSQL(deleteEData)

    }

    fun deleteMuscleItem(db:SQLiteDatabase, muscle: Muscle) {
        val deleteData = "DELETE FROM " + TABLE_MUSCLES + " WHERE " + COL_ID_MUSCLES + " = " +
                muscle.id + ";"
        db.execSQL(deleteData)
    }

    fun deleteExerciseMuscleItem(db:SQLiteDatabase, exerciseId: Int, muscleId: Int) {
        val deleteData = "DELETE FROM " + TABLE_EXERCISE_MUSCLES + " WHERE " + COL_EXERCISE_ID_EM + " = " +
                exerciseId + " AND " + COL_EXERCISE_ID_EM + " = " + muscleId + ";"
        db.execSQL(deleteData)
    }
    
    private fun deleteExerciseMuscleData(db:SQLiteDatabase, exerciseId: Int) {
        val deleteRData = "DELETE FROM " + TABLE_EXERCISE_MUSCLES + " WHERE " + COL_EXERCISE_ID_EM + " = " +
                exerciseId + ";"
        db.execSQL(deleteRData)
    }
    
    private fun deleteGroupExercisesData(db:SQLiteDatabase, groupId: Int) {
        val deleteRData = "DELETE FROM " + TABLE_GROUP_EXERCISES + " WHERE " + COL_GROUP_ID_GE + " = " +
                groupId + ";"
        db.execSQL(deleteRData)
    }

    fun deleteScheduleData(db:SQLiteDatabase, scheduleId: Int) {
        db.execSQL("DELETE FROM " + TABLE_SCHEDULES + " WHERE " + COL_SCHEDULE_ID + " = " +
                scheduleId + ";")
        db.execSQL("DELETE FROM " + TABLE_SCHEDULE_ITEMS + " WHERE " + COL_SCHEDULE_ID + " = " +
                scheduleId + ";")
    }

    private fun deleteScheduleItems(db: SQLiteDatabase, scheduleId: Int) {
        db.execSQL("DELETE FROM " + TABLE_SCHEDULE_ITEMS + " WHERE " + COL_SCHEDULE_ID + " = " +
                scheduleId + ";")
    }

    private fun deleteScheduleItem(db: SQLiteDatabase, scheduleId: Int, itemId: Int) {
        db.execSQL("DELETE FROM $TABLE_SCHEDULE_ITEMS WHERE" +
                " $COL_SCHEDULE_ID = $scheduleId AND $COL_ELEMENT_ID = $itemId;")
    }


    // helper methods
    private fun getExerciseFromName(db: SQLiteDatabase, name: String) : Exercise {
        lateinit var correctExercise: Exercise
        val exList = readExercisesData(db)
        for (ex in exList) {
            if (ex.name == name) {
                correctExercise = ex
            }
        }
        return correctExercise
    }

    private fun getExerciseFromId(db: SQLiteDatabase, id: Int) : Exercise {
        lateinit var correctExercise: Exercise
        val exList = readExercisesData(db)
        for (ex in exList) {
            if (ex.id == id) {
                correctExercise = ex
            }
        }
        return correctExercise
    }

    private fun Int.toBool() = this > 0

    private fun getGroupFromName(db: SQLiteDatabase, name: String) : Group {
        lateinit var correctGroup: Group
        val grList = readGroupData(db)
        for (gr in grList) {
            if (gr.name == name) {
                correctGroup = gr
            }
        }
        return correctGroup
    }

    private fun getGroupFromId(db: SQLiteDatabase, id: Int) : Group {
        lateinit var correctGroup: Group
        val grList = readGroupData(db)
        for (gr in grList) {
            if (gr.id == id) {
                correctGroup = gr
            }
        }
        return correctGroup
    }

    fun getGroupAndExerciseNames() : MutableList<String> {
        val listOfItems = readExercisesData(this.readableDatabase) + readGroupData(this.readableDatabase)
        val listOfNames: MutableList<String> = ArrayList()
        for (item in listOfItems) {
            listOfNames.add(item.name)
        }
        return listOfNames
    }
}