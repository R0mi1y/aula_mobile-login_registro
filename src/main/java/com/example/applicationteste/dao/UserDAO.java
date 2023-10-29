package com.example.applicationteste.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.applicationteste.activity.LoginActivity;
import com.example.applicationteste.helper.DBHelper;
import com.example.applicationteste.model.User;

public class UserDAO {
    private User user;
    private DBHelper db;

    public UserDAO (Context ctx, User user) {
        this.db = new DBHelper(ctx);
        this.user = user;
    }

    public boolean login() {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?;";
        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        if (this.user.getEmail().toString().isEmpty() || this.user.getPassword().toString().isEmpty()) return false;

        Cursor cr = dbLite.rawQuery(sql, new String[]{this.user.getEmail(), this.user.getPassword()});

        if (cr.getCount() > 0) return true;

        return false;
    }

    public boolean register() {
        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", this.user.getEmail());
        values.put("name", this.user.getName());
        values.put("phone", this.user.getPhone());
        values.put("password", this.user.getPassword());

        long newRowId = dbLite.insert("user", null, values);

        if (newRowId != -1) {
            Log.v("MeuApp", "Inserção bem-sucedida. ID da nova linha: " + newRowId);
            return true;
        } else {
            Log.e("MeuApp", "Erro na inserção dos dados. ID da nova linha: " + newRowId);
        }
        return false;
    }
}
