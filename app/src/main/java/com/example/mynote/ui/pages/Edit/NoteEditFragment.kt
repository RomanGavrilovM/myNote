package com.example.mynote.ui.pages.Edit;


import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.mynote.databinding.FragmentNoteEditBinding
import com.example.mynote.model.entities.NoteEntity
import java.text.SimpleDateFormat
import java.util.*

class NoteEditFragment : Fragment() {
    private var controller: Controller? = null
    private var binding: FragmentNoteEditBinding? = null
    private var note: NoteEntity? = null
    private var creationDate = 0L

    companion object {
        const val NOTE_ARGS_KEY = "NOTE_ARGS_KEY"
        @JvmStatic
        fun newInstance(item: NoteEntity?): NoteEditFragment {
            val noteEditFragment = NoteEditFragment()
            val bundle = Bundle()
            bundle.putParcelable(NOTE_ARGS_KEY, item)
            noteEditFragment.arguments = bundle
            return noteEditFragment
        }
    }

    interface Controller {
        fun openNotesListScreen(item: NoteEntity?)
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
        binding = FragmentNoteEditBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        args
        fillViews()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        controller = null
        super.onDestroy()
    }

    private fun setupListeners() {
        binding!!.saveNoteButton.setOnClickListener { v: View? ->
            createOrEditNote()
            controller!!.openNotesListScreen(note)
        }
        binding!!.dateTimeEditText.setOnClickListener { view: View? -> showDateTimeDialog(binding!!.dateTimeEditText) }
    }

    private fun createOrEditNote() {
        if (note == null) {
            note = NoteEntity(
                    binding!!.titleEditText.text.toString(),
                    binding!!.detailEditText.text.toString()
            )
        } else {
            note!!.title = binding!!.titleEditText.text.toString()
            note!!.detail = binding!!.detailEditText.text.toString()
        }
        if (creationDate != 0L) {
            note!!.setCreationDate(creationDate)
        }
    }

    private fun showDateTimeDialog(dateTimeEditText: EditText) {
        val calendar = Calendar.getInstance()
        val dateSetListener = OnDateSetListener { datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
            calendar[Calendar.YEAR] = year
            calendar[Calendar.MONTH] = month
            calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            val timeSetListener = OnTimeSetListener { timePicker: TimePicker?, hourOfDay: Int, minute: Int ->
                calendar[Calendar.HOUR_OF_DAY] = hourOfDay
                calendar[Calendar.MINUTE] = minute
                creationDate = calendar.timeInMillis
                val date = Date(creationDate)
                val dateFormat = SimpleDateFormat("dd.MM.y HH:mm", Locale.getDefault())
                dateTimeEditText.setText(dateFormat.format(date))
            }
            TimePickerDialog(context,
                    timeSetListener,
                    calendar[Calendar.HOUR_OF_DAY],
                    calendar[Calendar.MINUTE],
                    true).show()
        }
        DatePickerDialog(requireContext(),
                dateSetListener,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]).show()
    }

    private val args: Unit
        get() {
            val data = arguments
            if (data != null) note = data.getParcelable(NOTE_ARGS_KEY)
        }

    private fun fillViews() {
        if (note != null) {
            binding!!.titleEditText.setText(note!!.title)
            binding!!.detailEditText.setText(note!!.detail)
            binding!!.dateTimeEditText.setText(note!!.creationDate)
        }
    }


}