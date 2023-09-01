package org.acestream.engine.client.example;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.channels.Channel;
import java.util.ArrayList;
public class ChannelAdaptor extends RecyclerView.Adapter<ChannelAdaptor.ViewHolder>{

    private final Context context;
    private final ArrayList<ChannelModel> canales;

    // Constructor
    public ChannelAdaptor(Context context, ArrayList<ChannelModel> canales) {
        this.context = context;
        this.canales = canales;
    }






    @NonNull
    @Override
    public ChannelAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);
        return new ChannelAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChannelModel model = canales.get(position);
        holder.nombreCanal.setText(model.getNombre());
        holder.imagenCanal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInAceStream(false, model.getEnlace());
                Log.v("aaaa",model.getEnlace());
            }
        });
    }
    private void openInAceStream(boolean skipResolver,String enlace) {
        Intent intent = new Intent("org.acestream.action.start_content");
        intent.setData(Uri.parse("acestream:?content_id=" + enlace));
        if(skipResolver) {
            // Tell Ace Stream app to use its internal player for playback
            // Without this option Ace Stream app can show resolver (list of players to allow user
            // to select where to start playback).
            intent.putExtra("org.acestream.EXTRA_SELECTED_PLAYER", "{\"type\": 3}");
        }

        Intent chooser = Intent.createChooser(intent, "Select player");
        Log.v("bbbb",enlace);
        context.startActivity(chooser);
    }

    @Override
    public int getItemCount() {
        return canales.size();
    }







    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imagenCanal;
        private final TextView nombreCanal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenCanal = itemView.findViewById(R.id.imagenCanal);
            nombreCanal = itemView.findViewById(R.id.nombreCanal);
        }
    }
}
