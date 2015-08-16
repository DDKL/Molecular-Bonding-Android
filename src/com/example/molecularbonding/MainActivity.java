package com.example.molecularbonding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {
	public static List<Element> myElements;
	public Context context;
	private MyAdapter aa;
	List<ElementData> myElementData = new ArrayList<ElementData>();
	ContextMenu menus;
	String filename;
	String openfilepath;
	String en;

	private class MyAdapter extends ArrayAdapter<Element> {

		int resource;
		@SuppressWarnings("unused")
		Context context;

		public MyAdapter(Context _context, int _resource, List<Element> items) {
			super(_context, _resource, items);
			resource = _resource;
			context = _context;
			this.context = _context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout newView;
			Element w = myElements.get(position);

			// Inflate a new view if necessary.
			if (convertView == null) {
				newView = new LinearLayout(getContext());
				String inflater = Context.LAYOUT_INFLATER_SERVICE;
				LayoutInflater vi = (LayoutInflater) getContext()
						.getSystemService(inflater);
				vi.inflate(resource, newView, true);
			} else {
				newView = (LinearLayout) convertView;
			}

			
			TextView tv = (TextView) newView.findViewById(R.id.listText);
			tv.setText(myElementData.get(w.getAtomicNumber()).Name);

			return newView;
		}
	}

	Element holder;
///cretae context menu
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.listView1) {
			ListView lv = (ListView) v;

			menus = menu;
			AdapterView.AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) menuInfo;
			Element obj = (Element) lv.getItemAtPosition(acmi.position);
			holder = obj;
			if (obj.getAtomicNumber() == 0) {
				menu.setHeaderTitle("Select New Element");
				for (int i = 1; i < myElementData.size(); i++) {
					if (myElementData.get(i).type != 0) {
						menu.add(1, i, i, myElementData.get(i).Name);
					}
				}
			} else {
				menu.setHeaderTitle(myElementData.get(obj.getAtomicNumber()).Name);
				SubMenu atomicBondsMenu = menu.addSubMenu("Atomic bonds");
				menu.add(10, 1, 1, "Delete Element");
				for (int i = 0; i < obj.bonds.size(); i++) {
					SubMenu removeExistingBond = atomicBondsMenu.addSubMenu(0,
							Menu.FIRST, 0, myElementData.get(obj.bonds.get(i)
									.getAtomicNumber()).Name);
					removeExistingBond
							.setHeaderTitle("Delete Chemical Bond Between "
									+ myElementData.get(obj.bonds.get(i)
											.getAtomicNumber()).Name
									+ " and "
									+ myElementData.get(obj.getAtomicNumber()).Name
									+ "?");
					removeExistingBond.add(9, i, 0, "YES");
					removeExistingBond.add(19, 0, 0, "NO");
				}
				if (obj.bonds.size() < myElementData.get(obj.getAtomicNumber()).TotalBonds) {
					SubMenu addLinkmenu = atomicBondsMenu
							.addSubMenu("Add New Connection");
					SubMenu existing = addLinkmenu
							.addSubMenu("Connect to Existing Element");

					// Use existing elements
					if (myElementData.get(obj.getAtomicNumber()).type == 1) {
						for (int i = 1; i < myElements.size(); i++) {
							if (myElementData.get(myElements.get(i)
									.getAtomicNumber()).type >= 1
									&& myElements.get(i).bonds.size() < myElementData
											.get(myElements.get(i)
													.getAtomicNumber()).TotalBonds
									&& myElements.get(i) != obj) {
								existing.add(2, i, i, myElementData
										.get(myElements.get(i)
												.getAtomicNumber()).Name);
							}
						}
					}
					if (myElementData.get(obj.getAtomicNumber()).type == 2) {
						for (int i = 1; i < myElements.size(); i++) {
							if (myElementData.get(myElements.get(i)
									.getAtomicNumber()).type == 1
									|| myElementData.get(myElements.get(i)
											.getAtomicNumber()).type == 3
									&& myElements.get(i).bonds.size() < myElementData
											.get(myElements.get(i)
													.getAtomicNumber()).TotalBonds
									&& myElements.get(i) != obj) {
								existing.add(2, i, i, myElementData
										.get(myElements.get(i)
												.getAtomicNumber()).Name);
							}
						}
					}
					if (myElementData.get(obj.getAtomicNumber()).type == 3) {
						for (int i = 1; i < myElements.size(); i++) {
							if ((myElementData.get(myElements.get(i)
									.getAtomicNumber()).type == 1 || myElementData
									.get(myElements.get(i).getAtomicNumber()).type == 2)
									&& myElements.get(i).bonds.size() < myElementData
											.get(myElements.get(i)
													.getAtomicNumber()).TotalBonds
									&& myElements.get(i) != obj) {
								existing.add(2, i, i, myElementData
										.get(myElements.get(i)
												.getAtomicNumber()).Name);
							}
						}
					}

					// Create new element
					SubMenu newLink = addLinkmenu
							.addSubMenu("Connect to New Element");
					if (myElementData.get(obj.getAtomicNumber()).type == 1) {
						for (int i = 1; i < myElementData.size(); i++) {
							if (myElementData.get(i).type >= 1) {
								newLink.add(3, i, i, myElementData.get(i).Name
										+ "  ");
							}
						}
					}
					if (myElementData.get(obj.getAtomicNumber()).type == 2) {
						for (int i = 1; i < myElementData.size(); i++) {
							if (myElementData.get(i).type == 1
									|| myElementData.get(i).type == 3) {
								newLink.add(3, i, i, myElementData.get(i).Name);
							}
						}
					}
					if (myElementData.get(obj.getAtomicNumber()).type == 3) {
						for (int i = 1; i < myElementData.size(); i++) {
							if (myElementData.get(i).type == 1
									|| myElementData.get(i).type == 2) {
								newLink.add(3, i, i, myElementData.get(i).Name);
							}
						}
					}
				}
			}
		}
	}

	// When you press down on the Listview item this controls that
	// This is not complete but you shouldnt need it
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getGroupId() == 1) {
			Element el = new Element(item.getItemId());
			myElements.add(el);
			aa.notifyDataSetChanged();
			return true;
		} else if (item.getGroupId() == 2) {
			holder.addBond(myElements.get(item.getItemId()));
			return true;
		} else if (item.getGroupId() == 3) {
			Element el = new Element(item.getItemId());
			myElements.add(el);
			aa.notifyDataSetChanged();
			holder.addBond(el);
			return true;
		} else if (item.getGroupId() == 10) {
			holder.deleteAllBonds();
			myElements.remove(holder);
			holder = null;
			aa.notifyDataSetChanged();
			return true;
		} else if (item.getGroupId() == 9) {
			holder.deleteBond(holder.bonds.get(item.getItemId()));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	// Create and setup the listView and the myelementsData
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myElements = new ArrayList<Element>();
		filename = getIntent().getExtras().getString("filename");
		openfilepath = getIntent().getExtras().getString("open");

		TextFileHelper tx = new TextFileHelper(getApplicationContext());
		String data = tx.readTextFile(openfilepath);
		Log.e("File Data", data);
		String[] splitData = data.split("\n");
		if (openfilepath != null) {
			for (int i = 0; i < splitData.length; i++) {
				// String[] line = splitData[i].split(" ");
				if (i == 0) {
					Element tmp = new Element(0);
					myElements.add(tmp);
				} else {
					Element tmp = new Element(Integer.parseInt(splitData[i]));
					myElements.add(tmp);
				}

			}
			Log.e("file data", data);
		}

		aa = new MyAdapter(this, R.layout.fragment_main, myElements);
		ListView myListView = (ListView) findViewById(R.id.listView1);
		myListView.setAdapter(aa);
		aa.notifyDataSetChanged();
		InitializeElementData();
		registerForContextMenu(myListView);
	}

	// This opens the file call AtomicValues in assets and places their values
	// into myElementData
	void InitializeElementData() {
		try {
			AssetManager assetManager = getAssets();
			InputStream assetIn = assetManager.open("AtomicValues");
			String data = convertStreamToString(assetIn);
			String[] splitData = data.split("\n");
			for (int i = 0; i < splitData.length; i++) {
				String[] line = splitData[i].split(" ");
				ElementData tmp;
				if (i == 0) {
					tmp = new ElementData(Integer.parseInt(line[0]),
							Integer.parseInt(line[1]),
							Integer.parseInt(line[2]), "Add New Element");
				} else {
					tmp = new ElementData(Integer.parseInt(line[0]),
							Integer.parseInt(line[1]),
							Integer.parseInt(line[2]), line[3]);
				}
				myElementData.add(i, tmp);
			}
			// This is the "Add New Element button on the listView
			if (filename == null) {

			} else {
				Element base = new Element(0);
				myElements.add(base);
			}
			aa.notifyDataSetChanged();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String convertStreamToString(java.io.InputStream is) {
		@SuppressWarnings("resource")
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";

	}
//back press
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent in = new Intent(MainActivity.this, StartActivity.class);
		startActivity(in);
		finish();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
          //delete file
		case R.id.menu_delete:
			TextFileHelper tx2 = new TextFileHelper(getApplicationContext());
			tx2.deleteTextFile(openfilepath);
			Intent in = new Intent(MainActivity.this, StartActivity.class);
			startActivity(in);
			Toast.makeText(MainActivity.this, "File Deleted !",
					Toast.LENGTH_LONG).show();
			finish();

			return true;
			
			
			//save file
		case R.id.menu_save:
			TextFileHelper tx = new TextFileHelper(getApplicationContext());
			if (filename == null) {

				int a = openfilepath.lastIndexOf("/");
				String as = openfilepath.substring(a + 1,
						openfilepath.length() - 4);
				filename = as;
				tx.deleteTextFile(openfilepath);
			}
			en = "0";
			for (Element el : myElements) {

				if (en == "0") {
					en = el.getAtomicNumber() + "";
				} else {
					en = en + "\n" + el.getAtomicNumber();
				}

			}

			try {
				tx.addData(MainActivity.this, filename + ".txt", en);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent in1 = new Intent(MainActivity.this, StartActivity.class);
			startActivity(in1);
			Toast.makeText(MainActivity.this, "File Saved", Toast.LENGTH_LONG)
					.show();
			finish();

		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
