package pl.nygamichal.birthdaywishlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 07.08.2017.
 */

public class AdapterWishes extends RecyclerView.Adapter<AdapterWishes.ViewHolder>{
    List<Wish> wishes = new ArrayList<>();

    public AdapterWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)//s04e03 1:30
    {
       View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wish, parent, false);
        AdapterWishes.ViewHolder vh = new ViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)//ukazujemy wybrana notatke
    {
        Wish wish = wishes.get(position);
        holder.textViewContent.setText(wish.content);
        if (wish.owner!= null)
        {
            holder.textViewOwner.setText(wish.owner.name);
        }
        if (wish.photo == null)
        {
            holder.imageView.setVisibility(View.GONE);
        }
        else
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(wish.photo,0, wish.photo.length);
            if (bitmap == null)
            {
                holder.imageView.setVisibility(View.GONE);
            }
            holder.imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {//ile leementow ma ta lista
        return wishes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textViewContent;
        public TextView textViewOwner;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewContent = (TextView) itemView.findViewById(R.id.content);
            imageView= (ImageView) itemView.findViewById(R.id.imageView);
            textViewOwner = (TextView) itemView.findViewById(R.id.owner);
        }
    }
}
