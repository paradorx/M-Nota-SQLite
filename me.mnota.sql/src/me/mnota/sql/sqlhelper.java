package me.mnota.sql;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context dbContext) {
			
			super(dbContext, DATABASE_NAME, null, DATABASE_VERSION);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "+FAKULTI_TABLE+" ("
					+FAKULTI_ID+" TEXT PRIMARY KEY, "
					+FAKULTI_NAME+" TEXT NOT NULL);");
			
			db.execSQL("CREATE TABLE "+PROGRAM_TABLE+" ("
					+PROGRAM_ID+" TEXT PRIMARY KEY, "
					+PROGRAM_NAME+" TEXT NOT NULL);");
			
			db.execSQL("CREATE TABLE "+SUBJEK_TABLE+" ("
					+SUBJEK_ID+" TEXT PRIMARY KEY, "
					+SUBJEK_NAME+" TEXT NOT NULL);");
			
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
					+" SELECT 'TK' AS '"+PROGRAM_ID+"', 'PROGRAM SAINS KOMPUTER' AS '"+PROGRAM_NAME+"'"
					+" UNION SELECT 'SISM', 'PROGRAM SISTEM MAKLUMAT'"
					+" UNION SELECT 'TS', 'PROGRAM KECERDASAN BUATAN'"
					+" UNION SELECT 'TR', 'PROGRAM KOMPUTERAN INDUSTRI'"
					+" UNION SELECT 'SM', 'PROGRAM SAINS MAKLUMAT'"
					+" UNION SELECT 'MUL', 'PROGRAM MULTIMEDIA';");
			
			db.execSQL("INSERT INTO '"+SUBJEK_TABLE+"'"
					+" SELECT 'TTTM1923' AS '"+SUBJEK_ID+"', 'TEKNOLOGI MAKLUMAT DAN KEUSAHAWANAN' AS '"+SUBJEK_NAME+"'"
					+" UNION SELECT 'TTTK3043', 'ANALISIS DAN REKABENTUK AL-KHWARIZMI'"
					+" UNION SELECT 'TTTK3086', 'PROJEK'"
					+" UNION SELECT 'TTT3333', 'PENGURUSAN PERISIAN'"
					+" UNION SELECT 'TTT3223', 'PENGATURCARAAN RANGKAIAN';");
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXIST "+FAKULTI_TABLE);
			db.execSQL("DROP TABLE IF EXIST "+PROGRAM_TABLE);
			db.execSQL("DROP TABLE IF EXIST "+SUBJEK_TABLE);
			onCreate(db);
		}
		
	}
	
	public sqlhelper (Context c){
		ourContext = c;
	}
	
	public sqlhelper open() throws SQLException{
		ourDbHelper = new DbHelper(ourContext);
		ourDatabase = ourDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		ourDbHelper.close();
	}
	
	public ArrayList<String> getData(String RECEIVED_TABLE_NAME) throws SQLException{
		
		ArrayList<String> TABLE_LIST = new ArrayList<String>();
		String[] columns = new String[]{ RECEIVED_TABLE_NAME+"_id", RECEIVED_TABLE_NAME+"_name" };
		String result = "";
		
		Cursor c = ourDatabase.query(RECEIVED_TABLE_NAME, columns,
										null, null, null, null, null);
		
		int i_id =  c.getColumnIndex(RECEIVED_TABLE_NAME+"_id");
		int i_name =  c.getColumnIndex(RECEIVED_TABLE_NAME+"_name");
		
		for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			result = RECEIVED_TABLE_NAME+"_id:"+c.getString(i_id)+"\n"+RECEIVED_TABLE_NAME+"_name:"+c.getString(i_name);
			TABLE_LIST.add(result);
		}
		
		return TABLE_LIST;
		
	}
	
	public String getExactName(String RECEIVED_TABLE_NAME,long info) throws SQLException{
		
		String[] columns = new String[]{ RECEIVED_TABLE_NAME+"_id", RECEIVED_TABLE_NAME+"_name" };
		
		Cursor c = ourDatabase.query(RECEIVED_TABLE_NAME, columns, RECEIVED_TABLE_NAME+"_id="+info,
										null, null, null, null);
		
		if(c != null){
			c.moveToFirst();
			String ExactName = c.getString(1);
			return ExactName;
		}
		
		return "error";
	}
	
}