package com.example.molecularbonding;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;

import android.graphics.Bitmap;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

public class FiletListArrayAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private static final int DIALOG_LOADING = 0;
	List<String> listFile;
	Context activity;
	TextView name, path;

	Bitmap image;
	ProgressDialog progressDialog;



	public FiletListArrayAdapter(Context applicationContext,
			int simpleListItem1, ArrayList<String> from) {
		this.activity=applicationContext;
		this.listFile=from;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listFile.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View productListView = null;

		productListView = convertView;

		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (productListView == null)
			productListView = inflater.inflate(R.layout.menu_productlist_list,
					parent, false);

		name = (TextView) productListView.findViewById(R.id.name);

		path = (TextView) productListView.findViewById(R.id.path);
		 int a=listFile.get(position).lastIndexOf("/");
		 String as=listFile.get(position).substring(a+1, listFile.get(position).length());
		
		 
		 name.setText(as);
		 path.setText(listFile.get(position));
		 

		return productListView;
	}

}
