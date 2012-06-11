package me.mnota.sql;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class fakultiview extends ListActivity {
	
	private static final String TABLE_NAME="fakulti";
	private ArrayList<String> TABLE_LIST = new ArrayList<String>();
	private boolean itWork = true;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		sqlhelper info = new sqlhelper(this);
        info.open();
        setTABLE_LIST(info.getData(TABLE_NAME));
        info.close();
        
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getTABLE_LIST()));
        
        ListView list = getListView();
		list.setTextFilterEnabled(true);
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Toast.makeText(getApplicationContext(), ((TextView) arg1).getText(), Toast.LENGTH_SHORT).show();
			}
			
		});
		
	}
	
	public ArrayList<String> getTABLE_LIST() {
		return TABLE_LIST;
	}

	public void setTABLE_LIST(ArrayList<String> tABLE_LIST) {
		TABLE_LIST = tABLE_LIST;
	}
	
}
