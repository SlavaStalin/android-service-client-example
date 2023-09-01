package org.acestream.engine.client.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

import org.acestream.engine.ServiceClient;
import org.acestream.engine.controller.Callback;
import org.acestream.engine.service.v0.IAceStreamEngine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

//implements ServiceClient.Callback
public class AceStreamClientExample extends AppCompatActivity implements ServiceClient.Callback{

	private final static String TEST_CONTENT_ID_LIVE = "8ca07071b39185431f8e940ec98d1add9e561639";
	private ArrayList<String> mListItems = new ArrayList<>();
	private ArrayAdapter<String> mListAdapter;
	private ServiceClient mServiceClient;
	private EngineApi mEngineApi = null;
	private EngineSession mEngineSession = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rv_layout);
		RecyclerView rv = findViewById(R.id.idRVCourse);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		ArrayList<ChannelModel> canales = new ArrayList<>();
		APIManager apiManager = new APIManager();
		AcestreamElement[] acestreamElements = apiManager.ParseAPI();
		//TEST
		initializeAcestreamEngine();
		//Filtros
		AcestreamFilters filters = new AcestreamFilters(acestreamElements);
		buttonListeners(filters);
		updateRV(filters.filterLength());


		/*
		mServiceClient = new ServiceClient("ClientExample", this, this);

		mListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mListItems);
		ListView listView = findViewById(R.id.service_msg_list);
		listView.setAdapter(mListAdapter);

		Button btnBind = findViewById(R.id.btn_bind);
		btnBind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showMessage("Command: start engine");
				try {
					mServiceClient.startEngine();
				}
				catch(ServiceClient.ServiceMissingException e) {
					showMessage("Error: engine not found");
				}
			}
		});

		Button btnUnbind = findViewById(R.id.btn_unbind);
		btnUnbind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showMessage("Command: disconnect");
				unbind();
			}
		});

		findViewById(R.id.btn_start_session).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startSession();
			}
		});

		findViewById(R.id.btn_open_in_acestream_deprecated).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openInAceStreamDeprecated();
			}
		});

		findViewById(R.id.btn_open_in_acestream_show_resolver).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openInAceStream(false);
			}
		});

		findViewById(R.id.btn_open_in_acestream_skip_resolver).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openInAceStream(true);
			}
		});

		 */
	}
	public void initializeAcestreamEngine(){
		try {
			mServiceClient = new ServiceClient("ClientExample", this, this);
			mServiceClient.startEngine();
		}
		catch(ServiceClient.ServiceMissingException e) {
			showMessage("Error: engine not found");
		}

	}
	public void updateRV(AcestreamElement[] acestreamElements){
		RecyclerView rv = findViewById(R.id.idRVCourse);
		ArrayList<ChannelModel> canales = new ArrayList<>();
		for (AcestreamElement acestreamelement:
				acestreamElements) {
			canales.add(new ChannelModel(acestreamelement.getNombre(),acestreamelement.getEnlace()));
		}
		ChannelAdaptor channeladaptor = new ChannelAdaptor(this,canales);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		rv.setLayoutManager(linearLayoutManager);
		rv.setAdapter(channeladaptor);

	}
	public void buttonListeners(AcestreamFilters filters){
		ChipNavigationBar barra = findViewById(R.id.barra);
		barra.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
			@Override
			public void onItemSelected(int i) {
				switch(i){
					case R.id.all_button:
						updateRV(filters.filterLength());
						break;
					case R.id.movistar_button:
						updateRV(filters.filterMovistar());
						break;
					case R.id.dazn_button:
						updateRV(filters.filterDAZN());
						break;


				}

			}
		});
	}

	private void showMessage(final String text) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbind();
	}

	@Override
	public void onConnected(IAceStreamEngine service) {
		try {
			int engineApiPort = service.getEngineApiPort();
			int httpApiPort = service.getHttpApiPort();
			showMessage("Event: engine connected: engine_api_port=" + engineApiPort + " http_api_port=" + httpApiPort);
			if(mEngineApi == null) {
				mEngineApi = new EngineApi(service);
			}
		}
		catch(RemoteException e) {
			showMessage(e.getMessage());
		}
	}

	@Override
	public void onFailed() {
		showMessage("Event: engine failed");
	}

	@Override
	public void onDisconnected() {
		showMessage("Event: engine disconnected");
	}

	@Override
	public void onUnpacking() {
		showMessage("Event: engine unpacking");
	}

	@Override
	public void onStarting() {
		showMessage("Event: engine starting");
	}

	@Override
	public void onStopped() {
		showMessage("Event: engine stopped");
	}

	@Override
	public void onPlaylistUpdated() {
	}

	@Override
	public void onEPGUpdated() {
	}

	@Override
	public void onRestartPlayer() {
	}

	@Override
	public void onSettingsUpdated() {
	}

	private void unbind() {
		mServiceClient.unbind();
		mEngineApi = null;
	}

	private void startSession() {
		if(mEngineApi == null) {
			showMessage("missing engine api");
			return;
		}

		mEngineApi.startSession(TEST_CONTENT_ID_LIVE, new Callback<EngineSessionResponse>() {
			@Override
			public void onSuccess(EngineSessionResponse result) {
				if(!TextUtils.isEmpty(result.error)) {
					showMessage("Failed to start session: " + result.error);
					return;
				}

				mEngineSession = result.response;
				showMessage("session started: playbackUrl=" + result.response.playback_url);
				startPlayer(Uri.parse(result.response.playback_url));
			}

			@Override
			public void onError(String err) {
				showMessage("Failed to start session: " + err);
			}
		});
	}

	private void startPlayer(Uri playkackUri) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(playkackUri, "video/*");

		Intent chooser = Intent.createChooser(intent, "Select player");
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(chooser);
		}
		else {
			showMessage("No player found");
		}
	}

	/**
	 * This is deprecated way to start playback in Ace Stream app.
	 * Use this method for versions below 3.1.43.0
	 */

	private void openInAceStreamDeprecated() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("acestream://" + TEST_CONTENT_ID_LIVE));

		Intent chooser = Intent.createChooser(intent, "Select player");
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(chooser);
		}
		else {
			showMessage("No player found");
		}
	}

	/**
	 * This is the preferred way to start playback in Ace Stream app.
	 * This method is available for versions 3.1.43.0+
	 */

	public void openInAceStream(boolean skipResolver,String enlace) {
		Intent intent = new Intent("org.acestream.action.start_content");
		intent.setData(Uri.parse("acestream:?content_id=" + enlace));
		if(skipResolver) {
			// Tell Ace Stream app to use its internal player for playback
			// Without this option Ace Stream app can show resolver (list of players to allow user
			// to select where to start playback).
			intent.putExtra("org.acestream.EXTRA_SELECTED_PLAYER", "{\"type\": 3}");
		}

		Intent chooser = Intent.createChooser(intent, "Select player");
		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(chooser);
		}
		else {
			showMessage("No player found");
		}
	}

}
