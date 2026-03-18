package com.example.employee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EmployeeDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EMPLOYEE   = "employee";
    public static final String COL_ID           = "id";
    public static final String COL_NAME         = "name";
    public static final String COL_DEPARTMENT   = "department";
    public static final String COL_SALARY       = "salary";
    public static final String COL_DESIGNATION  = "designation";
    public static final String COL_EMAIL        = "email";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_EMPLOYEE + " (" +
            COL_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NAME        + " TEXT NOT NULL, " +
            COL_DEPARTMENT  + " TEXT NOT NULL, " +
            COL_SALARY      + " REAL NOT NULL, " +
            COL_DESIGNATION + " TEXT NOT NULL, " +
            COL_EMAIL       + " TEXT NOT NULL" +
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        seedData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(db);
    }

    private void seedData(SQLiteDatabase db) {
        Object[][] employees = {
            {"Alice Johnson",   "Engineering",  95000, "Senior Engineer",      "alice@corp.com"},
            {"Bob Smith",       "Engineering",  72000, "Junior Engineer",      "bob@corp.com"},
            {"Carol White",     "Marketing",    68000, "Marketing Manager",    "carol@corp.com"},
            {"David Brown",     "Marketing",    52000, "Marketing Analyst",    "david@corp.com"},
            {"Eva Green",       "HR",           60000, "HR Manager",           "eva@corp.com"},
            {"Frank Miller",    "HR",           45000, "HR Executive",         "frank@corp.com"},
            {"Grace Lee",       "Finance",      88000, "Finance Head",         "grace@corp.com"},
            {"Henry Wilson",    "Finance",      74000, "Financial Analyst",    "henry@corp.com"},
            {"Irene Clark",     "Engineering",  110000,"Tech Lead",            "irene@corp.com"},
            {"Jack Turner",     "Sales",        58000, "Sales Executive",      "jack@corp.com"},
            {"Karen Adams",     "Sales",        76000, "Sales Manager",        "karen@corp.com"},
            {"Leo Scott",       "Operations",  65000, "Operations Lead",      "leo@corp.com"},
            {"Mia Hall",        "Operations",  49000, "Operations Executive", "mia@corp.com"},
            {"Nathan Young",    "Engineering",  83000, "Software Engineer",    "nathan@corp.com"},
            {"Olivia King",     "Finance",      92000, "Senior Analyst",       "olivia@corp.com"},
        };

        for (Object[] emp : employees) {
            ContentValues cv = new ContentValues();
            cv.put(COL_NAME,        (String) emp[0]);
            cv.put(COL_DEPARTMENT,  (String) emp[1]);
            cv.put(COL_SALARY,      (int)    emp[2]);
            cv.put(COL_DESIGNATION, (String) emp[3]);
            cv.put(COL_EMAIL,       (String) emp[4]);
            db.insert(TABLE_EMPLOYEE, null, cv);
        }
    }

    /** Returns all employees */
    public List<Employee> getAllEmployees(StringBuilder queryOut) {
        String sql = "SELECT * FROM " + TABLE_EMPLOYEE + " ORDER BY " + COL_NAME;
        if (queryOut != null) queryOut.append(sql);
        return runQuery(sql, null);
    }

    /** Search by department */
    public List<Employee> getByDepartment(String department, StringBuilder queryOut) {
        String sql = "SELECT * FROM " + TABLE_EMPLOYEE +
                     " WHERE " + COL_DEPARTMENT + " = '" + department + "'" +
                     " ORDER BY " + COL_NAME;
        if (queryOut != null) queryOut.append(sql);
        return runQuery(
                "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + COL_DEPARTMENT + " = ? ORDER BY " + COL_NAME,
                new String[]{department}
        );
    }

    /** Search by salary range */
    public List<Employee> getBySalaryRange(double minSalary, double maxSalary, StringBuilder queryOut) {
        String sql = "SELECT * FROM " + TABLE_EMPLOYEE +
                     " WHERE " + COL_SALARY + " BETWEEN " + (int) minSalary + " AND " + (int) maxSalary +
                     " ORDER BY " + COL_SALARY + " DESC";
        if (queryOut != null) queryOut.append(sql);
        return runQuery(
                "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + COL_SALARY + " BETWEEN ? AND ? ORDER BY " + COL_SALARY + " DESC",
                new String[]{String.valueOf(minSalary), String.valueOf(maxSalary)}
        );
    }

    /** Search by department AND salary range */
    public List<Employee> getByDeptAndSalary(String department, double minSalary, double maxSalary, StringBuilder queryOut) {
        String sql = "SELECT * FROM " + TABLE_EMPLOYEE +
                     " WHERE " + COL_DEPARTMENT + " = '" + department + "'" +
                     " AND " + COL_SALARY + " BETWEEN " + (int) minSalary + " AND " + (int) maxSalary +
                     " ORDER BY " + COL_SALARY + " DESC";
        if (queryOut != null) queryOut.append(sql);
        return runQuery(
                "SELECT * FROM " + TABLE_EMPLOYEE + " WHERE " + COL_DEPARTMENT + " = ? AND " + COL_SALARY + " BETWEEN ? AND ? ORDER BY " + COL_SALARY + " DESC",
                new String[]{department, String.valueOf(minSalary), String.valueOf(maxSalary)}
        );
    }

    private List<Employee> runQuery(String sql, String[] args) {
        List<Employee> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Employee(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DEPARTMENT)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COL_SALARY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DESIGNATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    /** Returns distinct departments for spinner */
    public List<String> getDepartments() {
        List<String> depts = new ArrayList<>();
        depts.add("All Departments");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT " + COL_DEPARTMENT + " FROM " + TABLE_EMPLOYEE + " ORDER BY " + COL_DEPARTMENT,
                null
        );
        if (cursor.moveToFirst()) {
            do { depts.add(cursor.getString(0)); } while (cursor.moveToNext());
        }
        cursor.close();
        return depts;
    }
}
