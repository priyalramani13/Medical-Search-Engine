package trial.example.med10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context,Constants.DB_NAME,null,Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);

    }

    public long insertInfo(String name,String address,String phone,String image,String addTimeStamp,String updateTimeStamp) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.C_NAME,name);
        values.put(Constants.C_ADDRESS,address);
        values.put(Constants.C_PHONE,phone);
        values.put(Constants.C_IMAGE,image);
        values.put(Constants.C_ADD_TIMESTAMP,addTimeStamp);
        values.put(Constants.C_UPDATE_TIMESTAMP,updateTimeStamp);

        long id=db.insert(Constants.TABLE_NAME,null,values);
        db.close();
        return id;
    }

    public ArrayList<Model> getAllData(String orderBy) {

        ArrayList<Model> arrayList=new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToNext()) {
            do {
                Model model= new Model(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADDRESS)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIMESTAMP)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_UPDATE_TIMESTAMP))

                );

                arrayList.add(model);
            }while (cursor.moveToNext());
        }

        db.close();
        return arrayList;
    }
}
