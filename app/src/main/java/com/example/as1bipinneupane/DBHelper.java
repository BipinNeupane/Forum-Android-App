package com.example.as1bipinneupane;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    //database name
    public static final String DBNAME = "assignment1.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        String createUsersTblSQL = "create Table users(user_id INTEGER PRIMARY KEY AUTOINCREMENT,firstName TEXT,lastName TEXT,phone INTEGER, email TEXT NOT NULL UNIQUE,password TEXT, country TEXT,isAdmin INTEGER DEFAULT 0,dateRegistered DATE,dateUpdated DATE)";
        String createPostTblSQL = "CREATE TABLE posts (post_id INTEGER PRIMARY KEY AUTOINCREMENT, post TEXT, date_upload DATE,postedBy INTEGER, FOREIGN KEY(postedBy) REFERENCES users(user_id) ON DELETE CASCADE)";
        String createCommentTblSQL = "CREATE TABLE comments (comment_id INTEGER PRIMARY KEY AUTOINCREMENT, comment TEXT NOT NULL,comment_date DATE, commentBy INTEGER,forPost INTEGER, FOREIGN KEY (forPost) REFERENCES posts(post_id) ON DELETE CASCADE, FOREIGN KEY (commentBy) REFERENCES users(user_id) ON DELETE CASCADE)";
        MyDB.execSQL(createUsersTblSQL);
        MyDB.execSQL(createPostTblSQL);
        MyDB.execSQL(createCommentTblSQL);
        //        creating default admin account when app is created
        createAdminOnDefault(MyDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        String deleteUsersTblSQL = "drop Table if exists users";
        MyDB.execSQL(deleteUsersTblSQL);
    }

    //    creating a method to insert data
    public Boolean insertData(String firstname, String lastname, String email, String phone, String password, String country, String dateRegistered, String dateUpdated) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("password", password);
        contentValues.put("country", country);
        contentValues.put("dateRegistered", dateRegistered);
        contentValues.put("dateUpdated", dateUpdated);
        long result = MyDB.insert("users", null, contentValues);
        if (result == -1) return false;
        else
            return true;
    }

    private void createAdminOnDefault(SQLiteDatabase Db) {
        ContentValues values = new ContentValues();
        values.put("firstName", "admin");
        values.put("lastName", "admin");
        values.put("phone", "1413914");
        values.put("email", "admin@test.com");
        values.put("password", "admin");
        values.put("country", "admin");
        values.put("isAdmin", "1");
        values.put("dateRegistered", "2023-01-01");
        values.put("dateUpdated", "2023-01-01");
        Db.insert("users", null, values);

    }


    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean isEmailUnique(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor getUserDataByEmail(String email) {
        SQLiteDatabase DB = this.getReadableDatabase();

        String sql = "SELECT * FROM users WHERE email = ?";
        String[] selectionArgs = {email};
        Cursor cursor = DB.rawQuery(sql, selectionArgs);

        return cursor;
    }

    public String getUserIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT user_id FROM users WHERE email = ?";
        String[] args = {email};
        Cursor cursorid = db.rawQuery(sql, args);
        cursorid.moveToFirst();
        String id = cursorid.getString(0);
        return id;
    }


    //    creating a method to insert data
    public void updateUserData(String id, String firstname, String lastname, String email, String phone, String country, String dateUpdated) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("country", country);
        contentValues.put("dateUpdated", dateUpdated);
        String selection = "id = ?";
        String[] selectionArgs = {id};
        MyDB.update("users", contentValues, selection, selectionArgs);
    }

    public void postForum(String postContent,String dateUpload,String postedBy){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("post",postContent);
        contentValues.put("date_upload",dateUpload);
        contentValues.put("postedBy", postedBy);
        db.insert("posts",null,contentValues);
    }

    public Cursor getForumPosts(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT posts.*, users.firstName FROM posts JOIN users ON posts.postedBy = users.user_id order by post_id desc";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    public void deletePost(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "post_id = ?";
        String[] whereArgs = {id};
        db.delete("posts", whereClause, whereArgs);
    }

    public Cursor getComments(String postID){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectionArgs = {postID};
        String query = "SELECT u.firstName,u.user_id, c.* FROM comments c INNER JOIN users u ON c.commentBy = u.user_id WHERE c.forPost = ?";
        Cursor cursor = db.rawQuery(query,selectionArgs);
        return cursor;
    }

    public boolean postComment(String comment,String comment_date,String commentBy, String forPost){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("comment", comment);
        contentValues.put("comment_date",comment_date);
        contentValues.put("commentBy", commentBy);
        contentValues.put("forPost", forPost);
        long result = MyDB.insert("comments", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

//    to get user list from data base
    public Cursor getUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
//        selecting user_id!=1 because user_id 1 is always admin and only super admin can delete admins
        String query = "SELECT u.user_id, u.firstName,u.lastName,u.email FROM users u WHERE NOT user_id = 1";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }


    public void deleteComment(String comment_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "comment_id = ?";
        String[] whereArgs = {comment_id};
        db.delete("comments", whereClause, whereArgs);
    }

    public void deleteUser(String user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "user_id = ?";
        String[] whereArgs = {user_id};
        db.delete("users", whereClause, whereArgs);
    }

    public Cursor getUserPost(String userID){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] selectArgs = {userID};
        String query = "SELECT p.*, u.firstName FROM posts p JOIN users u ON p.postedBy = u.user_id WHERE postedBy = ? order by post_id desc";
        Cursor cursor = db.rawQuery(query,selectArgs);
        return cursor;
    }

    public void updatePost(String postID,String postContent){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("post",postContent);
        String selection = "post_id = ?";
        String[] selectionArgs = {postID};
        DB.update("posts", contentValues, selection, selectionArgs);

    }

}
