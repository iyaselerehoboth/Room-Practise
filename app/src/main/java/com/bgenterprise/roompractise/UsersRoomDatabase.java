package com.bgenterprise.roompractise;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Users.class}, version = 1, exportSchema = false)
public abstract class UsersRoomDatabase extends RoomDatabase {
    private static UsersRoomDatabase INSTANCE;

    //Table entity classes.
    public abstract UsersDAO getUsersDao();

    public static UsersRoomDatabase getFileDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),
                            UsersRoomDatabase.class,
                            "user-db")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
