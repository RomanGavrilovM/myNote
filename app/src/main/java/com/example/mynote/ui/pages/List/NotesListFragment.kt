package com.example.mynote.ui.pages.List;

import com.example.mynote.model.entities.NoteEntity
import com.example.mynote.App
import com.example.mynote.model.repos.NotesRepository
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.annotation.SuppressLint
import android.content.Context
import android.view.*
import android.widget.PopupMenu
import com.example.mynote.R
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mynote.Impl.NotesDiffCallback
import androidx.recyclerview.widget.DiffUtil
import com.example.mynote.databinding.FragmentNotesListBinding
import java.lang.IllegalStateException

class NotesListFragment : Fragment() {
    private val adapter = NotesAdapter()
    private val subscriber: Subscriber = object : Subscriber {
        override fun onNoteSaved(note: NoteEntity?) {
            if (note != null) {
                if (note.uid == null) {
                    repository!!.createNote(note)
                } else {
                    repository!!.updateNote(note.uid, note)
                }
                checkDiffRepo()
            }
        }
    }
    private var app: App? = null
    private var repository: NotesRepository? = null
    private var binding: FragmentNotesListBinding? = null
    private var controller: Controller? = null

    interface Controller {
        fun openNoteEditScreen(item: NoteEntity?)
        fun subscribe(subscriber: Subscriber?)
        fun unsubscribe(subscriber: Subscriber?)
    }
    interface Subscriber {
        fun onNoteSaved(note: NoteEntity?)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = if (context is Controller) {
            context
        } else {
            throw IllegalStateException("Activity must implement NotesListFragment.Controller")
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller!!.subscribe(subscriber)
        initRepository()
        initRecyclerView()
    }
    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
    override fun onDestroy() {
        controller!!.unsubscribe(subscriber)
        controller = null
        super.onDestroy()
    }
    private fun initRepository() {
        app = requireActivity().application as App
        repository = app!!.notesRepository
    }
    private fun initRecyclerView() {
        binding!!.recyclerView.layoutManager = LinearLayoutManager(context)
        binding!!.recyclerView.adapter = adapter
        adapter.data = repository!!.notes
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(item: NoteEntity, position: Int) {
                controller!!.openNoteEditScreen(item)
            }
            override fun onItemLongClick(item: NoteEntity, view: View, position: Int) {
                showNotePopupMenu(item, view)
            }
        })
    }
    private fun showNotePopupMenu(item: NoteEntity, view: View) {
        val popupMenu = PopupMenu(requireContext(), view, Gravity.END)
        popupMenu.inflate(R.menu.note_item_popup_menu)
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.edit_popup_menu_item -> controller!!.openNoteEditScreen(item)
                R.id.delete_popup_menu_item -> deleteItem(item)
            }
            true
        }
        popupMenu.show()
    }
    private fun deleteItem(item: NoteEntity) {
        if (repository!!.deleteNote(item.uid)) {
            Toast.makeText(requireActivity(),
                    getString(R.string.successfully_deleted) + item.title,
                    Toast.LENGTH_SHORT).show()
            checkDiffRepo()
        } else {
            Toast.makeText(requireActivity(),
                    R.string.fail_to_delete,
                    Toast.LENGTH_SHORT).show()
        }
    }
    public fun onNoteSaved(note: NoteEntity?) {
        if (note != null) {
            if (note.uid == null) {
                repository!!.createNote(note)
            } else {
                repository!!.updateNote(note.uid, note)
            }
            checkDiffRepo()
        }
    }

    fun checkDiffRepo() {
        val notesDiffCallback = NotesDiffCallback(adapter.data, repository!!.notes)
        val result = DiffUtil.calculateDiff(notesDiffCallback, true)
        adapter.data = repository!!.notes
        result.dispatchUpdatesTo(adapter)
    }
}