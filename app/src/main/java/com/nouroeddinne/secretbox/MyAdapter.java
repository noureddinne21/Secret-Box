package com.nouroeddinne.secretbox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private Context context;
    private static List<MyImages> imagesList;

    public MyAdapter(Context context, List<MyImages> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viwe = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(viwe);
    }

    public static MyImages getPostion (int position){
        return imagesList.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyImages myImages = imagesList.get(position);
        holder.title.setText(myImages.getImage_titel());
        holder.des.setText(myImages.getImage_description());
        Bitmap bitmap = BitmapFactory.decodeFile(myImages.getImage());
        if (bitmap != null) {
            holder.img.setImageBitmap(bitmap);
        }

        holder.linear_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddActivity.class);
                intent.putExtra("id", myImages.getImage_id());
                context.startActivity(intent);
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewImageActivity.class);
                intent.putExtra("img", myImages.getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,des;
        private ImageView img;
        private LinearLayout linear_show;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView1);
            des = itemView.findViewById(R.id.textView2);
            img = itemView.findViewById(R.id.imageView1);
            linear_show = itemView.findViewById(R.id.linear_show);
        }
    }























}
