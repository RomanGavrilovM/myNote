package com.example.mynote.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynote.R;
import com.example.mynote.domain.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleTextView = itemView.findViewById(R.id.title_text_view);
    private final TextView detailTextView = itemView.findViewById(R.id.detail_text_view);
    private final TextView dateTextView = itemView.findViewById(R.id.date_text_view);

    private Note note;

    public NoteViewHolder(@NonNull ViewGroup parent, NotesAdapter.OnItemClickListener clickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        itemView.setOnClickListener(view -> clickListener.onItemClick(note));
    }

    public void bind(Note note) {
        this.note = note;
        titleTextView.setText(note.getTitle());
        detailTextView.setText(note.getDetail());
        dateTextView.setText(note.getCreationDate());
    }
}
