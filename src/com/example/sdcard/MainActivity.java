package com.example.sdcard;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	private File file;
	private List<String> myList;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		myList = new ArrayList<String>();

		String root_sd = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
		file = new File(root_sd );
		File list[] = file.listFiles();

		for (int i = 0; i < list.length; i++) {
			myList.add(list[i].getName());
		}

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, myList));

	}

	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		super.onListItemClick(l, v, position, id);

		File temp_file = new File(file, myList.get(position));

		if (!temp_file.isFile()) 
		{
			file = new File(file, myList.get(position));
			File list[] = file.listFiles();

			myList.clear();

			for (int i = 0; i < list.length; i++) 
			{
//				myList.add(list[i].getName());
				
				for(int j=0;j<list.length;j++)
				{
					if(list[i].getName().regionMatches(true, 0, list[j].getName(), 0, 3)) 
					{ 
						if(i!=j)
						{
							Log.d("xxxxx", "files:"+list[i].getName()); 
							myList.add(list[i].getName());
						}
					}
				}
			}
			Toast.makeText(getApplicationContext(), file.toString(),
					Toast.LENGTH_LONG).show();
			setListAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, myList));

		}
		else
		{
			Toast.makeText(getApplicationContext(), file.toString()+" Not a File",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onBackPressed() {
		String parent = file.getParent().toString();
		
		Log.d("yyyy", "file address:"+parent);
		if(!parent.equals("/storage/emulated"))
		{	
			file = new File(parent);
			File list[] = file.listFiles();

			myList.clear();

			for(int i = 0; i < list.length; i++) 
			{
				myList.add(list[i].getName());
			}
			Toast.makeText(getApplicationContext(), parent, Toast.LENGTH_LONG)
				.show();
			setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, myList));

		}
	}
}