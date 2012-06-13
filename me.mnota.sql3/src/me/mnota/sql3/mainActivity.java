package me.mnota.sql3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class mainActivity extends Activity implements OnClickListener {
	
	private Intent i;
	EditText namafakulti, namaprogram, namasubjek;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //set button listener
        findViewById(R.id.buttonFakulti).setOnClickListener(this);
    }

    //setOnClickListener onClick action
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.buttonFakulti:
			i = new Intent(this, viewfakulti.class);
            startActivity(i);
			break;
		
		}
	}
}