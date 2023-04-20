package com.example.rickandmorty.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rickandmorty.R;
import com.example.rickandmorty.model.CharacterModel;

import java.util.List;


public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.MyViewHolder> {

    private Context context;
    private List<CharacterModel> characterList;
    private ItemClickListener clickListener;

    public CharacterListAdapter(Context context, List<CharacterModel> characterList, ItemClickListener clickListener) {
        this.context = context;
        this.characterList = characterList;
        this.clickListener = clickListener;
    }

    public void setCharacterList(List<CharacterModel> characterList){
        this.characterList = characterList;
        notifyDataSetChanged();
    }

    public void clear() {
        characterList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharacterListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_character,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context)
                .load(this.characterList.get(position).getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imgCharacter);
        holder.txtCharacterName.setText(this.characterList.get(position).getName().toString());
        holder.txtCharacterSpecies.setText(this.characterList.get(position).getSpecies().toString());
        holder.txtCharacterGender.setText(this.characterList.get(position).getGender().toString());
        if ("male".equals(holder.txtCharacterGender.getText().toString().toLowerCase())){
            holder.imgGender.setImageResource(R.drawable.gender_male);
        }else if ("female".equals(holder.txtCharacterGender.getText().toString().toLowerCase())){
            holder.imgGender.setImageResource(R.drawable.gender_female);
        }else {
            holder.imgGender.setImageResource(R.drawable.gender_unknown);
        }
        holder.txtCharacterStatus.setText(this.characterList.get(position).getStatus().toString());
        if ("dead".equals(holder.txtCharacterStatus.getText().toString().toLowerCase())){
            holder.imgStatus.setImageResource(R.drawable.status_dead);
        } else if ("alive".equals(holder.txtCharacterStatus.getText().toString().toLowerCase())) {
            holder.imgStatus.setImageResource(R.drawable.status_alive);
        }else {
            holder.imgStatus.setImageResource(R.drawable.status_unknown);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onCharacterClick(characterList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.characterList == null ? 0 : this.characterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCharacter, imgGender, imgStatus;
        TextView txtCharacterName,txtCharacterSpecies,txtCharacterStatus,txtCharacterGender;
        public MyViewHolder(View itemView){
            super(itemView);
            imgCharacter = itemView.findViewById(R.id.imgCharacter);
            txtCharacterName = itemView.findViewById(R.id.txtCharacterName);
            txtCharacterSpecies = itemView.findViewById(R.id.txtCharacterSpecies);
            txtCharacterStatus = itemView.findViewById(R.id.txtCharacterStatus);
            txtCharacterGender = itemView.findViewById(R.id.txtCharacterGender);

            imgGender = itemView.findViewById(R.id.imgGender);
            imgStatus = itemView.findViewById(R.id.imgStatus);
        }
    }

    public interface ItemClickListener{
        public void onCharacterClick(CharacterModel character);
    }
}
