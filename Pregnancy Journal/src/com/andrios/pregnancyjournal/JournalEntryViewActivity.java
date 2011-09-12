package com.andrios.pregnancyjournal;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class JournalEntryViewActivity extends Activity {
	

	static final int DATE_DIALOG_ID = 1;
	
	ArrayList<JournalEntry> noteList;
	int index, mMonth, mDay, mYear;
	
	TextView dateLBL;
	EditText commentsTXT, titleTXT;
	JournalEntry note;
	Button saveBTN;
	OnClickListener myOnClickListener;
	Button newBTN;
	Spinner moodSpinner;
	CheckBox morningSickCheckBox, importantCheckBox;
	RadioButton boy, girl;

	private String array_spinner[];
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.journalentryviewactivity);
        
     
        getExtras();
        setConnections();
        setOnClickListeners();
        
    }
    
	@SuppressWarnings("unchecked")
	private void getExtras() {
		Intent intent = this.getIntent();
		noteList = (ArrayList<JournalEntry>) intent.getSerializableExtra("list");
		index = intent.getIntExtra("index", -1);
		if(index == -1){
			note = new JournalEntry();
			
		}else{
			note = noteList.get(index);
		}
		
	}
	
	private void setConnections() {
		array_spinner=new String[8];
		array_spinner[0]="Excited";
		array_spinner[1]="Happy";
		array_spinner[2]="Scared";
		array_spinner[3]="Hopeful";
		array_spinner[4]="Worried";
		array_spinner[5]="Crying";
		array_spinner[6]="Uncertain";
		array_spinner[7]="Mad";
		moodSpinner = (Spinner) findViewById(R.id.journalEntryViewActivityMoodSpinner);
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, array_spinner);
		moodSpinner.setAdapter(adapter);
		moodSpinner.setSelection(note.getMood());
		
		saveBTN = (Button) findViewById(R.id.journalEntryViewActivitySaveBTN);
		
		dateLBL = (TextView) findViewById(R.id.journalEntryViewActivityDateLBL);
		dateLBL.setText(note.getDateString());
		
		morningSickCheckBox = (CheckBox) findViewById(R.id.journalEntryViewMorningSickCheckBox);
		morningSickCheckBox.setChecked(note.getMorningSick());
		
		
		importantCheckBox = (CheckBox) findViewById(R.id.journalEntryViewActivityImportantCheckBox);
		importantCheckBox.setChecked(note.getImportant());
		
		
		commentsTXT = (EditText) findViewById(R.id.journalEntryViewActivityCommentsTXT);
		commentsTXT.setText(note.getNotes());
		
		boy = (RadioButton) findViewById(R.id.journalEntryViewWishingBoyRDO);
		girl = (RadioButton) findViewById(R.id.journalEntryViewWishingGirlRDO);
		if(note.wishingChanged){
			boy.setChecked(note.getWishingBoy());
			girl.setChecked(!note.getWishingBoy());
		}
		
		titleTXT = (EditText) findViewById(R.id.journalEntryViewActivityTitleTXT);
		titleTXT.setText(note.getTitle());
	
	}
	
	private void setOnClickListeners(){
		dateLBL.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
				
			}
			
		});
		
		saveBTN.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(checkFormat()){
					Intent intent = new Intent();
					note.setNotes(commentsTXT.getText().toString().trim());
					note.setMood(moodSpinner.getSelectedItemPosition());
					note.setMorningSick(morningSickCheckBox.isChecked());
					note.setTitle(titleTXT.getText().toString().trim());
					note.setImportant(importantCheckBox.isChecked());
					if(boy.isChecked() || girl.isChecked()){
						note.setWishingBoy(boy.isChecked());
					}
					
					if(index == -1){
						noteList.add(note);
					}else{
						noteList.set(index, note);
					}
					
					
					intent.putExtra("list", noteList);
				System.out.println("SAVE BUTTON CLICKED");
					JournalEntryViewActivity.this.setResult(RESULT_OK, intent);
					JournalEntryViewActivity.this.finish();
				}
				
			}

			

			
			
		});
	}
	
	private boolean checkFormat() {
		// TODO Auto-generated method stub
		return true;
	}
	
	  @Override
	    protected Dialog onCreateDialog(int id) {
	            switch (id) {

	            case DATE_DIALOG_ID:
	                    return new DatePickerDialog(this,
	                            mDateSetListener,
	                            note.getDate().get(Calendar.YEAR), 
	                            note.getDate().get(Calendar.MONTH), 
	                            note.getDate().get(Calendar.DAY_OF_MONTH));
	            }
	            return null;
	    }
	    protected void onPrepareDialog(int id, Dialog dialog) {
	            switch (id) {

	            case DATE_DIALOG_ID:
	                    ((DatePickerDialog) dialog).updateDate(
	                    		note.getDate().get(Calendar.YEAR), 
	                            note.getDate().get(Calendar.MONTH), 
	                            note.getDate().get(Calendar.DAY_OF_MONTH));
	                    break;
	            }
	    }    
	   
	    private DatePickerDialog.OnDateSetListener mDateSetListener =
	            new DatePickerDialog.OnDateSetListener() {

					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						
	                    note.setDate(dayOfMonth, monthOfYear, year);
	                    dateLBL.setText(note.getDateString());
						
					}

	          
	    };


}