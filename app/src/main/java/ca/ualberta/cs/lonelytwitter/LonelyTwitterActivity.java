package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

	private static final String FILENAME = "file.sav"; // Model

    private LonelyTwitterActivity activity = this;

	public Button getSaveButton() {
		return saveButton;
	}

	public EditText getBodyText() {
		return bodyText;
	}

	private Button saveButton;
	private EditText bodyText; // View

	public ListView getOldTweetsList() {
		return oldTweetsList;
	}

	private ListView oldTweetsList; // View

	public ArrayList<Tweet> getTweets() {
		return tweets;
	}

	private ArrayList<Tweet> tweets = new ArrayList<Tweet>(); // Controller
	ArrayAdapter<Tweet> adapter; // View

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState); // Part of the UI, goes in View as it doesn't change part of the model.
		setContentView(R.layout.main); // View

		Tweet tweet = new ImportantTweet(""); // Model

		ArrayList<Tweet> tweetList; // Model, but not being used.

		bodyText = (EditText) findViewById(R.id.body); // View
		saveButton = (Button) findViewById(R.id.save); // View
		Button clearButton = (Button) findViewById(R.id.clear); // View
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList); // View

		// save button event listener.
		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString(); // Controller, because it is adding a new tweet: we're modifying the model.
				tweets.add(new NormalTweet(text)); // Controller
				adapter.notifyDataSetChanged(); // View, not actually modifying the state of the model.
				saveInFile(); // Model, because it is changing stuff on the disk and not for the user, not in the view: so cant be view or controller.
			}
		});

        oldTweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                				Intent intent = new Intent(activity, EditTweetActivity.class);
                				startActivity(intent);
                			}
            		});

		// clear button event listener.
		clearButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				tweets.clear(); // Controller
				adapter.notifyDataSetChanged(); // View
				saveInFile(); // Model
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart(); // View
		loadFromFile(); // Model, because it changes the local data stored, but the view has not yet changed.
		adapter = new ArrayAdapter<Tweet>(this, R.layout.list_item, tweets); // View
		oldTweetsList.setAdapter(adapter); // View

	}

	// This entire method is part of Model, user doesn't see change here.
	private void loadFromFile() {
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Gson gson = new Gson();
			// Taken from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
			Type listType = new TypeToken<ArrayList<NormalTweet>>() {}.getType();
			tweets = gson.fromJson(in, listType);
		} catch (FileNotFoundException e) {
			// If the file cannot be found, make a blank file.
			tweets = new ArrayList<Tweet>();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// This entire method is part of Model, user doesn't see change here.
	private void saveInFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME, 0);
			OutputStreamWriter writer = new OutputStreamWriter(fos);
			Gson gson = new Gson();
			gson.toJson(tweets, writer);
			writer.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}