package me.mnota.sql3;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

public class viewsubjek extends ListActivity {
	
	static String parentId;
	
	private static final String TABLE_NAME="subjek";
	private static final String TABLE_PARENT_NAME="program";
	private ArrayList<String> TABLE_LIST = new ArrayList<String>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//try to make a SQLite commands to get all the list with the same parent id
		try{
			
			sqlhelper info = new sqlhelper(this);
	        info.open();
	        setTABLE_LIST(info.getListNameFromId(TABLE_NAME, parentId, TABLE_PARENT_NAME));
	        info.close();
			
		}catch (Exception e){
			
			String error = e.toString();
			Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
			
		}
		
setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getTABLE_LIST()));
		
		ListView list = getListView();
		list.setTextFilterEnabled(true);
		
		list.setOnItemClickListener(new OnItemClickListener(){
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int listviewposition, long listviewid) {
				
				String viewgettext = (String) ((TextView) view).getText();
				openMain(viewgettext);
				openDialog();
				
			}
			
		});
		
	}

	public ArrayList<String> getTABLE_LIST() {
		return TABLE_LIST;
	}

	public void setTABLE_LIST(ArrayList<String> tABLE_LIST) {
		TABLE_LIST = tABLE_LIST;
	}
	
	public static void setFakultiId(String parentId2) {
		parentId = parentId2;
	}
	
	protected void openMain(String viewgettext) {
		
		sqlhelper info = new sqlhelper(this);
		info.open();
		String thisId = info.getSelectedId(TABLE_NAME, viewgettext);
		info.close();
		Toast.makeText(getApplicationContext(), thisId, Toast.LENGTH_SHORT).show();
		
	}
	
	//just a dialog example
	protected void openDialog(){
		
		final CharSequence[] items = {"Red", "Green", "Blue"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pick a color");
		
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        Toast.makeText(getApplicationContext(), items[item],
								Toast.LENGTH_SHORT).show();
		    }
		});
		
		AlertDialog alert = builder.create();
		alert.show();
		
	}

}