package com.example.molecularbonding;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;



import java.util.ArrayList;




import android.content.Context;
import android.os.Environment;
import android.util.Log;



public class TextFileHelper {


	Context mContext;

	/*
	 * Constructor
	 */
	public TextFileHelper(Context mContext) {
		this.mContext = mContext;
	}

	/*
	 * file create and save
	 */
	public void writeToFile(String fileName, String body) {
		FileOutputStream fos = null;

		try {
			final File dir = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath());

			if (!dir.exists()) {
				dir.mkdirs();
			}

			final File myFile = new File(dir, fileName + ".txt");

			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			else{
			deleteTextFile(dir+fileName+".txt");	
			}

			fos = new FileOutputStream(myFile);

			fos.write(body.getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Read a text file.
	 */
	@SuppressWarnings("resource")
	public String readTextFile(String actualFile) {

		String contents = "";

		try {

			// Get the text file
			File file = new File(actualFile);

			// check if file is not empty
			if (file.exists() && file.length() != 0) {

				// read the file to get contents
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;

				while ((line = br.readLine()) != null) {
					// store the text file line to contents variable
					contents += line + "\n";
				}

			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contents;
	}

	/*
	 * Update a text file.
	 */
	@SuppressWarnings("static-access")
	public void addData(Context context, String filename, String data)
			throws IOException {
		FileOutputStream fout;
		DataOutputStream dout;
		fout = context.openFileOutput(filename, context.MODE_APPEND);
		dout = new DataOutputStream(fout);

		dout.writeUTF(data);

	}

	/*
	 * Delete a text file.
	 */
	public void deleteTextFile(String actualFile) {
		try {

			File file = new File(actualFile);

			if (file.exists()) {

				if (file.delete()) {
					
				}

			} 

		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
    //get list of file
	public ArrayList<String> ListDir(Context context) {
		
		File root = context.getApplicationContext().getFilesDir();
		Log.e("path", Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		ArrayList<String> fileList = new ArrayList<String>();

		File[] files = root.listFiles();

		for (File file : files) {
			fileList.add(file.getPath());
		}
		return fileList;

	}

}