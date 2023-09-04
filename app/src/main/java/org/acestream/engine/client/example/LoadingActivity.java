package org.acestream.engine.client.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;

import org.acestream.engine.ServiceClient;
import org.acestream.engine.service.v0.IAceStreamEngine;

public class LoadingActivity extends AppCompatActivity implements ServiceClient.Callback{
    private ServiceClient mServiceClient;
    private EngineApi mEngineApi = null;
    private EngineSession mEngineSession = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        try {
            mServiceClient = new ServiceClient("ClientExample", this, this);
            mServiceClient.startEngine();
        } catch (ServiceClient.ServiceMissingException e) {
            Intent myIntent = new Intent(LoadingActivity.this, AceStreamClientExample.class);
            LoadingActivity.this.startActivity(myIntent);
            //throw new RuntimeException(e);
        }

    }

    @Override
    public void onConnected(IAceStreamEngine service) {
        try {
            int engineApiPort = service.getEngineApiPort();
            int httpApiPort = service.getHttpApiPort();
            //ShowMessage("Event: engine connected: engine_api_port=" + engineApiPort + " http_api_port=" + httpApiPort);
            if(mEngineApi == null) {
                mEngineApi = new EngineApi(service);
            }

        }
        catch(RemoteException e) {
            //showMessage(e.getMessage());
        }
        //Intent myIntent = new Intent(LoadingActivity.this, AceStreamClientExample.class);
        //LoadingActivity.this.startActivity(myIntent);
    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onUnpacking() {

    }

    @Override
    public void onStarting() {

    }

    @Override
    public void onStopped() {

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
}