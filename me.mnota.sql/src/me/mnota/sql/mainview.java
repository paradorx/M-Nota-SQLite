package me.mnota.sql;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class mainview extends Activity implements OnClickListener {
	
	private Intent i;
	EditText namafakulti, namaprogram, namasubjek;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        findViewById(R.id.buttonFakulti).setOnClickListener(this);
        findViewById(R.id.buttonProgram).setOnClickListener(this);
        findViewById(R.id.buttonSubjek).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
		case R.id.buttonFakulti:
			i = new Intent(this, fakultiview.class);
            startActivity(i);
			break;
			
		case R.id.buttonProgram:
			i = new Intent(this, programview.class);
            startActivity(i);
			break;
			
		case R.id.buttonSubjek:
			i = new Intent(this, subjectview.class);
            startActivity(i);
			break;
		
		}
	}
}