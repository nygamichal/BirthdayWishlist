package pl.nygamichal.birthdaywishlist;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Admin on 07.08.2017.
 */

public class AdapterWishes extends RecyclerView.Adapter<AdapterWishes.ViewHolder>{
    List<Wish> wishes = new ArrayList<>();
    Realm realm;
    public AdapterWishes(List<Wish> wishes, Realm realm) {
        this.wishes = this.wishes;
        this.realm = realm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)//s04e03 1:30
    {
       View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wish, parent, false);
        AdapterWishes.ViewHolder vh = new ViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)//ukazujemy wybrana notatke
    {
        final Wish wish = wishes.get(position);
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
        if (wish.isDone)
        {
            holder.textViewDone.setVisibility(View.VISIBLE);
            holder.buttonDone.setVisibility(View.GONE);
        }
        else
        {
            holder.textViewDone.setVisibility(View.GONE);
            holder.buttonDone.setVisibility(View.VISIBLE);
        }
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alertBuild = new AlertDialog.Builder(view.getContext());
                alertBuild.setTitle("Delete item");
                alertBuild.setMessage("Are you sure?");
                alertBuild.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeWish(holder, wish);
                    }
                });
                alertBuild.setNegativeButton("Cancel",null);
                alertBuild.show();
                return false;
            }
        });
        holder.buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.beginTransaction();
                wish.isDone = true;
                realm.copyToRealmOrUpdate(wish);
                realm.commitTransaction();
                holder.textViewDone.setVisibility(View.VISIBLE);
                holder.buttonDone.setVisibility(View.GONE);
            }
        });
    }

    private void removeWish(ViewHolder holder, Wish wish) {
        wishes.remove(holder.getAdapterPosition());
        realm.beginTransaction();
        wish.deleteFromRealm();
        realm.commitTransaction();
        AdapterWishes.this.notifyDataSetChanged();
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
        public TextView textViewDone;
        public Button buttonDone;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewContent = (TextView) itemView.findViewById(R.id.content);
            imageView= (ImageView) itemView.findViewById(R.id.imageView);
            textViewOwner = (TextView) itemView.findViewById(R.id.owner);
            textViewDone = (TextView) itemView.findViewById(R.id.textDone);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            buttonDone = (Button) itemView.findViewById(R.id.buttonDone);
        }
    }
}
