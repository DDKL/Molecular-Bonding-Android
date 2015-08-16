package com.example.molecularbonding;


import java.util.ArrayList;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class StartActivity extends Activity {

	ArrayList<String> s;
	ArrayList<String> from;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.start_activity);
	
			Button btnAddNewNote = (Button) findViewById(R.id.addnotebutton);
			
          
			btnAddNewNote.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder alert = new AlertDialog.Builder(
							StartActivity.this);

					alert.setTitle("Create New File");
					alert.setMessage("Enter File Name");

					// Set an EditText view to get user input
					final EditText input = new EditText(StartActivity.this);
					input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
							18) // 18 is max length
					});
					input.addTextChangedListener(new TextWatcher() {
						@Override
						public void afterTextChanged(Editable s) {
							if (input.length() >= 18) {
								showToast();
							}
						}

						@Override
						public void onTextChanged(CharSequence s, int st,
								int b, int c) {
						}

						@Override
						public void beforeTextChanged(CharSequence s, int st,
								int c, int a) {
						}
					});

					alert.setView(input);

					alert.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									String filename = input.getText()
											.toString();
									if (filename.length() <= 18) {
										
										for(String s:from){
											int a=s.lastIndexOf("/");
											 String as=s.substring(a+1, s.length()-4);
											 if(as.trim().equals(filename)){
												Toast.makeText(StartActivity.this, "File name is already exist! Please choose another name", Toast.LENGTH_LONG).show();
											 }
											 else{
												 Intent in = new Intent(
															StartActivity.this,
															MainActivity.class);
													in.putExtra("filename", input.getText()
															.toString());
													startActivity(in);

													StartActivity.this.finish();
											 }
											 
										}
										
										
										
									} 
								}
							});

					alert.setNegativeButton("Exit",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// App close.
									StartActivity.this.finish();
								}
							});

					alert.show();
				}
			});
			
		// intailize list
			ListView lst = (ListView) findViewById(R.id.list);
          //get file list from folder
			TextFileHelper tx = new TextFileHelper(StartActivity.this);
			s = tx.ListDir(StartActivity.this);
			from = new ArrayList<String>();
          // file list desort
			for (int i = s.size() - 1; i >= 0; i--) {
			
				from.add(s.get(i));
			}
			
			//check internal storage folder 
			if (s.size() >= 1) {

				FiletListArrayAdapter adapter = new FiletListArrayAdapter(getApplicationContext(),
						android.R.layout.simple_list_item_1, from);
				lst.setAdapter(adapter);
			} else {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);

				alert.setTitle("Do you want to create a file");
				alert.setMessage("Enter File Name");

				// Set an EditText view to get user input
				final EditText input = new EditText(this);
				input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
						18) // 18 is max length
				});
				
				//check filename length
				input.addTextChangedListener(new TextWatcher() {
					@Override
					public void afterTextChanged(Editable s) {
						if (input.length() >= 18) {
							showToast();
						}
					}

					@Override
					public void onTextChanged(CharSequence s, int st, int b,
							int c) {
					}

					@Override
					public void beforeTextChanged(CharSequence s, int st,
							int c, int a) {
					}
				});

				alert.setView(input);
				alert.setPositiveButton("New",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String filename = input.getText()
										.toString();
								if (filename.length() <= 18) {
								// go to create file to 
								Intent in = new Intent(StartActivity.this,
										MainActivity.class);
								in.putExtra("filename", input.getText()
										.toString());
								startActivity(in);

								StartActivity.this.finish();
								}

								
							}
						});

				alert.setNegativeButton("Exit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Close aplication
								StartActivity.this.finish();
							}
						});

				alert.show();

			}

			lst.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent in = new Intent(StartActivity.this,
							MainActivity.class);
					in.putExtra("open", from.get(arg2));
					Log.e("file", from.get(arg2));
					finish();
					startActivity(in);
				}
			});
		}

	

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
   // show toast
	public void showToast() {
		Toast.makeText(this, "File name should not more than 18 character",
				Toast.LENGTH_LONG).show();
		
	}

}