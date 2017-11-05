package occupi.occupi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseConn extends SQLiteOpenHelper
{
    private static String TAG = "DataBaseConn";
    private static String DB_PATH = "";
    private static String DB_NAME ="occupiDB";
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    //Connects the application to the sqlite database
    public DataBaseConn(Context context)
    {
        super(context, DB_NAME, null, 1);
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
    }

    //Close the connection to the sqlite database
    @Override
    public synchronized void close()
    {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}