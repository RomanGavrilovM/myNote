package com.example.mynote.ui;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


import com.example.mynote.model.entities.Note;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<Note> data = new ArrayList<>();
    private OnItemClickListener clickListener = null;

    public void setData(List<Note> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<Note> getData(){
        return new ArrayList<>(data);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(parent, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    private Note getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }
}