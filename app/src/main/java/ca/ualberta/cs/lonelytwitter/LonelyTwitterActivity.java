package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private LonelyTwitterActivity activity= this;

	public ListView getOldTweetsList() {
		return oldTweetsList;
	}

	private ListView oldTweetsList;

	public ArrayList<Tweet> getTweets() {
		return tweets;
	}

	private ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	private ArrayAdapter<Tweet> adapter;
	private Button saveButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState); // view
		setContentView(R.layout.main); //view
		bodyText = (EditText) findViewById(R.id.body); //view
		Button clearButton = (Button) findViewById(R.id.clear); //view
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList); //view
		setSaveButton();
		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK); // view
				String text = bodyText.getText().toString(); // controller
				tweets.add(new NormalTweet(text)); //controller
				adapter.notifyDataSetChanged(); // view
				saveInFile(); //model

			}


		});

		oldTweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(activity, EditTweetActivity.class);
				intent.putExtra("sentTweet", tweets.get(position).toString());
				startActivity(intent);
			}
		});

		clearButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK); // view
				tweets.clear(); //controller
				adapter.notifyDataSetChanged(); //view
				saveInFile(); // model
			}

		});
	}


	public void setSaveButton(){
		saveButton = (Button) findViewById(R.id.save); //view
	}

	public Button getSaveButton(){
		return saveButton;
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart(); //view
		loadFromFile(); //model
		adapter = new ArrayAdapter<Tweet>(this,
				R.layout.list_item, tweets); // View
		oldTweetsList.setAdapter(adapter); // view
	}

	/////// This method is part of model
	private void loadFromFile() {
		//ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<NormalTweet>>(){}.getType();
			tweets = gson.fromJson(in, listType);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//throw new RuntimeException(e);
			tweets=new ArrayList<Tweet>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

	}

	/////// This method is part of model
	private void saveInFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					0);
			//fos.write(new String(date.toString() + " | " + text)
			//		.getBytes());
			OutputStreamWriter writer = new OutputStreamWriter(fos);
			Gson gson = new Gson();
			gson.toJson(tweets, writer);
			writer.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public EditText getBodyText() {
		return bodyText;
	}

	public void editTweet(int ID, String text) {

	}

	public ArrayAdapter<Tweet> getArrayAdapter(){
		return adapter;
	}

	public void updateAdapter(){
		adapter.notifyDataSetChanged();
	}

}