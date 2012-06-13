package me.mnota.sql3;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** CLASS TO HANDLE SQLite PROCESS */
public class sqlhelper {
	
	public static final String DATABASE_NAME = "mnotasql.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String FAKULTI_TABLE = "fakulti";
	public static final String FAKULTI_ID = "fakulti_id";
	public static final String FAKULTI_NAME = "fakulti_name";
	
	public static final String PROGRAM_TABLE = "program";
	public static final String PROGRAM_ID = "program_id";
	public static final String PROGRAM_NAME = "program_name";
	
	public static final String SUBJEK_TABLE = "subjek";
	public static final String SUBJEK_ID = "subjek_id";
	public static final String SUBJEK_NAME = "subjek_name";
	
	private DbHelper ourDbHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private class DbHelper extends SQLiteOpenHelper{

		//database init
		public DbHelper(Context dbContext) {
			super(dbContext, DATABASE_NAME, null, DATABASE_VERSION);
		}

		//create table
		/** only will be executed once */ 
		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL("CREATE TABLE "+FAKULTI_TABLE+" ("
					+FAKULTI_ID+" TEXT PRIMARY KEY, "
					+FAKULTI_NAME+" TEXT NOT NULL"
					+");");
			
			db.execSQL("CREATE TABLE "+PROGRAM_TABLE+" ("
					+PROGRAM_ID+" TEXT PRIMARY KEY, "
					+PROGRAM_NAME+" TEXT NOT NULL, "
					+FAKULTI_ID+" TEXT NOT NULL, FOREIGN KEY ("+FAKULTI_ID+") REFERENCES "+FAKULTI_TABLE+" ("+FAKULTI_ID+")"
					+");");
			
			db.execSQL("CREATE TABLE "+SUBJEK_TABLE+" ("
					+SUBJEK_ID+" TEXT PRIMARY KEY, "
					+SUBJEK_NAME+" TEXT NOT NULL, "
					+PROGRAM_ID+" TEXT NOT NULL, FOREIGN KEY ("+PROGRAM_ID+") REFERENCES "+PROGRAM_TABLE+" ("+PROGRAM_ID+")"
					+");");
			
			db.execSQL("INSERT INTO '"+FAKULTI_TABLE+"'"
					+" SELECT 'FEP' AS '"+FAKULTI_ID+"', 'FAKULTI EKONOMI DAN PENGURUSAN' AS '"+FAKULTI_NAME+"'"
					+" UNION SELECT 'FFARMASI', 'FAKULTI FARMASI'"
					+" UNION SELECT 'FKAB', 'FAKULTI KEJURUTERAAN DAN ALAM BINA'"
					+" UNION SELECT 'FPEN', 'FAKULTI PENDIDIKAN'"
					+" UNION SELECT 'FPI', 'FAKULTI PENGAJIAN ISLAM'"
					+" UNION SELECT 'FPERGIGIAN', 'FAKULTI PERGIGIAN'"
					+" UNION SELECT 'FPERUBATAN', 'FAKULTI PERUBATAN'"
					+" UNION SELECT 'FST', 'FAKULTI SAINS DAN TEKNOLOGI'"
					+" UNION SELECT 'FSK', 'FAKULTI SAINS KESIHATAN'"
					+" UNION SELECT 'FSSK', 'FAKULTI SAINS SOSIAL DAN KEMANUSIAAN'"
					+" UNION SELECT 'FTSM', 'FAKULTI TEKNOLOGI DAN SAINS MAKLUMAT'"
					+" UNION SELECT 'FUU', 'FAKULTI UNDANG-UNDANG'"
					+" UNION SELECT 'PPS', 'PUSAT PENGAJIAN SISWAZAH PERNIAGAAN';");
			
			db.execSQL("INSERT INTO '"+PROGRAM_TABLE+"'"
					+" SELECT 'TK' AS '"+PROGRAM_ID+"', 'PROGRAM SAINS KOMPUTER' AS '"+PROGRAM_NAME+"', 'FTSM' AS '"+FAKULTI_ID+"'"
					+" UNION SELECT 'SISM', 'PROGRAM SISTEM MAKLUMAT', 'FTSM'"
					+" UNION SELECT 'TS', 'PROGRAM KECERDASAN BUATAN', 'FTSM'"
					+" UNION SELECT 'TR', 'PROGRAM KOMPUTERAN INDUSTRI', 'FTSM'"
					+" UNION SELECT 'SM', 'PROGRAM SAINS MAKLUMAT', 'FTSM'"
					+" UNION SELECT 'MUL', 'PROGRAM MULTIMEDIA', 'FTSM'"
					+" UNION SELECT 'TEKNO', 'PROGRAM TEKNOLOGI KIMIA', 'FST'"
					+" UNION SELECT 'UBAT', 'PROGRAM UBAT FARMASI', 'FFARMASI'"
					+";");
			
			db.execSQL("INSERT INTO '"+SUBJEK_TABLE+"'"
					//+" SELECT 'TTTK3086' AS '"+SUBJEK_ID+"', 'PROJEK' AS '"+SUBJEK_NAME+"'"
					+" SELECT 'TTTK3086' AS '"+SUBJEK_ID+"', 'PROJEK' AS '"+SUBJEK_NAME+"', 'TK' AS '"+PROGRAM_ID+"'"
					+" UNION SELECT 'TTTK3043', 'ANALISIS DAN REKABENTUK AL-KHWARIZMI', 'TK'"
					//+" UNION SELECT 'TTTK3086', 'PROJEK'"
					+" UNION SELECT 'TTTM1923', 'TEKNOLOGI MAKLUMAT DAN KEUSAHAWANAN', 'TK'"
					+" UNION SELECT 'TTTK3333', 'PENGURUSAN PERISIAN', 'TK'"
					+" UNION SELECT 'TTTK3223', 'PENGATURCARAAN RANGKAIAN', 'TK'"
					+";");
			
		}

		/** executed only if the application has been already installed */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL("DROP TABLE IF EXIST "+FAKULTI_TABLE);
			db.execSQL("DROP TABLE IF EXIST "+PROGRAM_TABLE);
			db.execSQL("DROP TABLE IF EXIST "+SUBJEK_TABLE);
			onCreate(db);
			
		}
		
	}
	
	//sqlhelper init
	public sqlhelper (Context c){
		ourContext = c;
	}
	
	//open database
	public sqlhelper open() throws SQLException{
		ourDbHelper = new DbHelper(ourContext);
		ourDatabase = ourDbHelper.getWritableDatabase();
		return this;
	}
	
	//close database
	public void close(){
		ourDbHelper.close();
	}
	
	//return ALL name list of the requested table
	public ArrayList<String> getListName(String RECEIVED_TABLE_NAME) throws SQLException{
		
		ArrayList<String> TABLE_LIST = new ArrayList<String>();
		String[] columns = new String[]{ "*" };
		String result;
		
		Cursor c = ourDatabase.query(RECEIVED_TABLE_NAME, columns, null, null, null, null, null);
		int i_name =  c.getColumnIndex(RECEIVED_TABLE_NAME+"_name");
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			result = c.getString(i_name);
			TABLE_LIST.add(result);
		}
		
		return TABLE_LIST;
		
	}
	
	//return id of selected items in the requested table
	public String getSelectedId(String tableName, String viewgettext) throws SQLException{
		
		String[] columns = new String[]{ "*" };
		
		/** get query based on items name since its unique */
		Cursor c = ourDatabase.query(tableName, columns, tableName+"_name=\""+viewgettext+"\"", null, null, null, null);
		
		if(c != null){
			c.moveToFirst();
			/** c.getString(0) is to get string from column 0 in which refers to item's id */
			String name = c.getString(0);
			return name;
		}
		
		return null;
		
	}
	
	//return name list of the requested table based on its parent table id
	public ArrayList<String> getListNameFromId(String RECEIVED_TABLE_NAME, String parentID, String tableParentName) throws SQLException{
		
		// TODO
		ArrayList<String> TABLE_LIST = new ArrayList<String>();
		String[] columns = new String[]{ "*" };
		String result;
		
		
		Cursor c = ourDatabase.query(RECEIVED_TABLE_NAME, columns, tableParentName+"_id=\""+parentID+"\"", null, null, null, null);
		int i_name =  c.getColumnIndex(RECEIVED_TABLE_NAME+"_name");
		
		if(c != null){
			for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
				result = c.getString(i_name);
				TABLE_LIST.add(result);
			}
			return TABLE_LIST;
		}
		
		return null;
		
	}
	
}
