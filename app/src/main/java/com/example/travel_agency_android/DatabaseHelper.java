package com.example.travel_agency_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import TablesHelper.AccommodationTable;
import TablesHelper.AirfareTable;
import TablesHelper.EntertainmentTable;
import TablesHelper.GasolineTable;
import TablesHelper.MealsTable;
import TablesHelper.TravelsTable;
import TablesHelper.UsersTable;
import models.AccommodationModelDB;
import models.AirfareModelDB;
import models.EntertainmentModelDB;
import models.GasolineModelDB;
import models.MealModelDB;
import models.TravelModelDB;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "travel_agency.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + UsersTable.TABLE_NAME + " (" +
                UsersTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsersTable.COL_EMAIL + " TEXT, " +
                UsersTable.COL_PASSWORD + " TEXT)");

        db.execSQL("CREATE TABLE " + TravelsTable.TABLE_NAME + " (" +
                TravelsTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TravelsTable.COL_TRAVEL_NAME + " TEXT, " +
                TravelsTable.COL_DESCRIPTION + " TEXT, " +
                TravelsTable.COL_QUANTITY_OF_PEOPLE + " INTEGER, " +
                TravelsTable.COL_TRAVEL_DURATION + " INTEGER, " +
                TravelsTable.COL_DEPARTURE_LOCATION + " TEXT, " +
                TravelsTable.COL_ARRIVAL_LOCATION + " TEXT, " +
                TravelsTable.COL_TRANSPORTATION_MODE + " TEXT)");

        db.execSQL("CREATE TABLE " + GasolineTable.TABLE_NAME + " (" +
                GasolineTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GasolineTable.COL_TRAVEL_ID + " INTEGER, " +
                GasolineTable.COL_TOTAL_KM + " REAL, " +
                GasolineTable.COL_MEDIA_KM_LITRES + " REAL, " +
                GasolineTable.COL_AVERAGE_COST_PER_LITER + " REAL, " +
                GasolineTable.COL_NUMBER_OF_VEHICLES + " INTEGER, " +
                GasolineTable.COL_TOTAL + " REAL, " +
                "FOREIGN KEY (" + GasolineTable.COL_TRAVEL_ID + ") REFERENCES "
                + TravelsTable.TABLE_NAME + "(" + TravelsTable.COL_ID + "))");

        db.execSQL("CREATE TABLE " + AirfareTable.TABLE_NAME + " (" +
                AirfareTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AirfareTable.COL_TRAVEL_ID + " INTEGER, " +
                AirfareTable.COL_ESTIMATED_COST_PER_PERSON + " REAL, " +
                AirfareTable.COL_VEHICLE_RENTAL + " REAL, " +
                AirfareTable.COL_TOTAL + " REAL, " +
                "FOREIGN KEY (" + AirfareTable.COL_TRAVEL_ID + ") REFERENCES " +
                TravelsTable.TABLE_NAME + "(" + TravelsTable.COL_ID + "))");

        db.execSQL("CREATE TABLE " + MealsTable.TABLE_NAME + " (" +
                MealsTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MealsTable.COL_TRAVEL_ID + " INTEGER, " +
                MealsTable.COL_MEAL_COST + " REAL, " +
                MealsTable.COL_MEALS_PER_DAY + " INTEGER, " +
                MealsTable.COL_TOTAL + " REAL, " +
                "FOREIGN KEY (" + AirfareTable.COL_TRAVEL_ID + ") REFERENCES " +
                TravelsTable.TABLE_NAME + "(" + TravelsTable.COL_ID + "))");

        db.execSQL("CREATE TABLE " + AccommodationTable.TABLE_NAME + " (" +
                AccommodationTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AccommodationTable.COL_TRAVEL_ID + " INTEGER, " +
                AccommodationTable.COL_COST_PER_NIGHT + " REAL, " +
                AccommodationTable.COL_TOTAL_OF_NIGHTS + " INTEGER, " +
                AccommodationTable.COL_TOTAL_OF_ROOMS + " INTEGER, " +
                AccommodationTable.COL_TOTAL + " TEXT)");

        db.execSQL("CREATE TABLE " + EntertainmentTable.TABLE_NAME + " (" +
                EntertainmentTable.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EntertainmentTable.COL_TRAVEL_ID + " INTEGER," +
                EntertainmentTable.COL_OPTION_1 + " INTEGER DEFAULT 0," +
                EntertainmentTable.COL_OPTION_2 + " INTEGER DEFAULT 0," +
                EntertainmentTable.COL_OPTION_3 + " INTEGER DEFAULT 0," +
                EntertainmentTable.COL_OPTION_4 + " INTEGER DEFAULT 0," +
                EntertainmentTable.COL_OPTION_5 + " INTEGER DEFAULT 0," +
                EntertainmentTable.COL_OPTION_6 + " INTEGER DEFAULT 0," +
                EntertainmentTable.COL_OPTION_7 + " INTEGER DEFAULT 0," +
                EntertainmentTable.COL_OPTION_8 + " INTEGER DEFAULT 0," +
                EntertainmentTable.COL_OPTION_9 + " INTEGER DEFAULT 0," +
                EntertainmentTable.COL_OPTION_10 + " INTEGER DEFAULT 0," +
                EntertainmentTable.COL_TOTAL + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS " + UsersTable.TABLE_NAME);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TravelsTable.TABLE_NAME);
        MyDB.execSQL("DROP TABLE IF EXISTS " + AirfareTable.TABLE_NAME);
        MyDB.execSQL("DROP TABLE IF EXISTS " + MealsTable.TABLE_NAME);
        MyDB.execSQL("DROP TABLE IF EXISTS " + AccommodationTable.TABLE_NAME);
        MyDB.execSQL("DROP TABLE IF EXISTS " + EntertainmentTable.TABLE_NAME);
        onCreate(MyDB);
    }

    public boolean insertUser(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersTable.COL_EMAIL, email);
        contentValues.put(UsersTable.COL_PASSWORD, password);
        long result = MyDatabase.insert(UsersTable.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + UsersTable.TABLE_NAME + " WHERE "
                + UsersTable.COL_EMAIL + " = ?", new String[]{email});
        return cursor.getCount() > 0;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM " + UsersTable.TABLE_NAME + " WHERE "
                + UsersTable.COL_EMAIL + " = ? AND " + UsersTable.COL_PASSWORD
                + " = ?", new String[]{email, password});
        return cursor.getCount() > 0;
    }

    public long insertTravel(TravelModelDB travel) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TravelsTable.COL_TRAVEL_NAME, travel.getTravelName());
        contentValues.put(TravelsTable.COL_DESCRIPTION, travel.getDescription());
        contentValues.put(TravelsTable.COL_QUANTITY_OF_PEOPLE, travel.getNumberOfPeople());
        contentValues.put(TravelsTable.COL_TRAVEL_DURATION, travel.getTravelDuration());
        contentValues.put(TravelsTable.COL_DEPARTURE_LOCATION, travel.getDepartureLocation());
        contentValues.put(TravelsTable.COL_ARRIVAL_LOCATION, travel.getArrivalLocation());
        contentValues.put(TravelsTable.COL_TRANSPORTATION_MODE, travel.getTransportationMode());

        long result = MyDatabase.insert(TravelsTable.TABLE_NAME, null, contentValues);

        return result;
    }

    public boolean insertGasoline(GasolineModelDB gasoline) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(GasolineTable.COL_TRAVEL_ID, gasoline.getTravelId());
        contentValues.put(GasolineTable.COL_TOTAL_KM, gasoline.getTotalKm());
        contentValues.put(GasolineTable.COL_MEDIA_KM_LITRES, gasoline.getAverageKmPerLiter());
        contentValues.put(GasolineTable.COL_AVERAGE_COST_PER_LITER, gasoline.getAverageCostPerLiter());
        contentValues.put(GasolineTable.COL_NUMBER_OF_VEHICLES, gasoline.getNumberOfVehicles());
        contentValues.put(GasolineTable.COL_TOTAL, gasoline.getTotal());

        long result = MyDatabase.insert(GasolineTable.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean insertAirfare(AirfareModelDB airfare) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(AirfareTable.COL_TRAVEL_ID, airfare.getTravelId());
        contentValues.put(AirfareTable.COL_ESTIMATED_COST_PER_PERSON, airfare.getEstimatedCostPerPerson());
        contentValues.put(AirfareTable.COL_VEHICLE_RENTAL, airfare.getVehicleRental());
        contentValues.put(AirfareTable.COL_TOTAL, airfare.getTotal());

        long result = MyDatabase.insert(AirfareTable.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean insertMeal(MealModelDB meal) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MealsTable.COL_TRAVEL_ID, meal.getTravelId());
        contentValues.put(MealsTable.COL_MEAL_COST, meal.getMealCost());
        contentValues.put(MealsTable.COL_MEALS_PER_DAY, meal.getMealsPerDay());
        contentValues.put(MealsTable.COL_TOTAL, meal.getTotal());

        long result = MyDatabase.insert(MealsTable.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean insertAccommodation(AccommodationModelDB accommodation) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(AccommodationTable.COL_TRAVEL_ID, accommodation.getTravelId());
        contentValues.put(AccommodationTable.COL_COST_PER_NIGHT, accommodation.getEstimatedCostPerPerson());
        contentValues.put(AccommodationTable.COL_TOTAL_OF_NIGHTS, accommodation.getTotalNights());
        contentValues.put(AccommodationTable.COL_TOTAL_OF_ROOMS, accommodation.getTotalRooms());
        contentValues.put(AccommodationTable.COL_TOTAL, accommodation.getTotal());

        long result = database.insert(AccommodationTable.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean insertEntertainment(EntertainmentModelDB entertainment) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(EntertainmentTable.COL_TRAVEL_ID, entertainment.getTravelId());
        contentValues.put(EntertainmentTable.COL_OPTION_1, entertainment.getOption1());
        contentValues.put(EntertainmentTable.COL_OPTION_2, entertainment.getOption2());
        contentValues.put(EntertainmentTable.COL_OPTION_3, entertainment.getOption3());
        contentValues.put(EntertainmentTable.COL_OPTION_4, entertainment.getOption4());
        contentValues.put(EntertainmentTable.COL_OPTION_5, entertainment.getOption5());
        contentValues.put(EntertainmentTable.COL_OPTION_6, entertainment.getOption6());
        contentValues.put(EntertainmentTable.COL_OPTION_7, entertainment.getOption7());
        contentValues.put(EntertainmentTable.COL_OPTION_8, entertainment.getOption8());
        contentValues.put(EntertainmentTable.COL_OPTION_9, entertainment.getOption9());
        contentValues.put(EntertainmentTable.COL_OPTION_10, entertainment.getOption10());
        contentValues.put(EntertainmentTable.COL_TOTAL, entertainment.getTotal());

        long result = database.insert(EntertainmentTable.TABLE_NAME, null, contentValues);
        return result != -1;
    }
}
