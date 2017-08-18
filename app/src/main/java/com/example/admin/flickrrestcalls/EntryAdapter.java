package com.example.admin.flickrrestcalls;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Admin on 8/17/2017.
 */

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {

    public static final String TAG = "Adapter";
    AlertDialog alertDialog;
    ArrayList<Entry> entryList;
    Context context;

    public EntryAdapter(ArrayList<Entry> entryList){
        this.entryList = entryList;
    }

    @Override
    public EntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EntryAdapter.ViewHolder holder, int position) {
        holder.tvAuthor.setText(entryList.get(position).getAuthor());
        holder.tvDateTaken.setText(entryList.get(position).getDate_taken());
        //TODO convert to bitmap and scale with layout
        final String URL = entryList.get(position).getPictureURL();
        Glide.with(context).load(URL).thumbnail(0.2f).into(holder.picture);


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                Log.d(TAG, "onClick: ");
                final AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.picture_dialog, null);
                builder.setView(dialogView);

                ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.options, R.layout.support_simple_spinner_dropdown_item);
                ListView lv = (ListView) dialogView.findViewById(R.id.thumbOptions);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String action = ((TextView) view).getText().toString();

                        switch(action){
                            case "Show Full Image":
                                alertDialog.cancel();
                                Intent intent = new Intent(context, PictureActivity.class);
                                intent.putExtra("URL", URL);
                                context.startActivity(intent);
                                break;
                            case "Show Small Image":
                                alertDialog.cancel();
                                AlertDialog.Builder builder1;
                                builder1 = new AlertDialog.Builder(context);
                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View pictureView = inflater.inflate(R.layout.picture_fragment, null);
                                builder1.setView(pictureView);
                                ImageView iView = (ImageView) pictureView.findViewById(R.id.imageView);
                                Glide.with(context).load(URL).into(iView);
                                AlertDialog dialog = builder1.create();
                                dialog.show();
                                break;
                        }
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        if(entryList != null){
            return entryList.size();
        }else {
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor, tvDateTaken;
        ImageView picture;

        public ViewHolder(View itemView) {
            super(itemView);
            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvDateTaken = (TextView) itemView.findViewById(R.id.tvDateTaken);
            picture = (ImageView) itemView.findViewById(R.id.picture);

        }
    }

}
