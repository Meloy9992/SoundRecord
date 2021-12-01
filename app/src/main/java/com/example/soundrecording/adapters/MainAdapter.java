package com.example.soundrecording.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundrecording.R;
import com.example.soundrecording.ViewActivity;
import com.example.soundrecording.dataBase.ConstantsDb;
import com.example.soundrecording.dataBase.ManagerDb;

import java.util.ArrayList;
import java.util.List;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ItemHolder> {

    private Context context;
    private List<ListAudio> listAudio;

    public MainAdapter(Context context) {
        this.context = context;
        listAudio = new ArrayList<>();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_record, parent, false);
        return new ItemHolder(view, context, listAudio);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.setRecName(listAudio.get(position).getRecName());
    }

    @Override
    public int getItemCount() {
        return listAudio.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewRecName;
        private Context context;
        private List<ListAudio> listAudios;

        public ItemHolder(@NonNull View view, Context context, List<ListAudio> listAudio) {
            super(view);
            this.context = context;
            this.listAudios = listAudio;
            textViewRecName = view.findViewById(R.id.tvTitle);
            view.setOnClickListener(this);
        }

        public void setRecName(String recName){
            textViewRecName.setText(recName);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ViewActivity.class);
            intent.putExtra(ConstantsDb.LIST_ITEM_INTENT, listAudio.get(getAdapterPosition()));
            context.startActivity(intent);
            }
        }

    public void updateAdapter(List<ListAudio> newList) {
        listAudio.clear(); // УДАЛЕНИЕ ВСЕХ ЭЛЕМЕНТОВ В СПИСКЕ
        listAudio.addAll(newList); // ДОБАВЛЕНИЕ НОВЫХ ЭЛЕМЕНТОВ В СПИСОК
        notifyDataSetChanged(); // ОБНОВЛЕНИЕ
    }

    public void removeItem(int position, ManagerDb manager){
        manager.deleteFromDb(listAudio.get(position).getId());
        listAudio.remove(position);
        notifyItemRangeChanged(0, listAudio.size());
        notifyItemRemoved(position);
    }
    }
