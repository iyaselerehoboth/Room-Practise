package com.bgenterprise.roompractise;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsersDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void InsertUsers(Users... user);

    @Update
    void UpdateUser(Users... user);

    @Delete
    void DeleteUser(Users user);

    @Query("SELECT * FROM users_table WHERE username = :username and password = :password")
    List<Users> selectSingleUser(String username, String password);

}
