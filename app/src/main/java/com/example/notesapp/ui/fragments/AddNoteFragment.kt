package com.example.notesapp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.database.AppDatabase
import com.example.notesapp.database.AppRepository
import com.example.notesapp.model.Notes
import com.example.notesapp.ui.activities.MainActivity
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.viewmodel.NotesViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.notes_fragment.*
import java.util.*

class AddNoteFragment : Fragment(R.layout.fragment_add_note) {


    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository  = AppRepository(AppDatabase(requireActivity()))
        val viewModelFactory = NotesViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(NotesViewModel::class.java)
        var type = resources.getStringArray(R.array.type)[0]

        id_type.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                type = resources.getStringArray(R.array.type)[position]
                Log.d("taget",type)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        })
        back_button.setOnClickListener{
            findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
        }

        id_iv.setOnClickListener {
            if(!TextUtils.isEmpty(id_heading.text.toString()) && !TextUtils.isEmpty(id_body.text.toString())){
                val head =  id_heading.text.toString()
                val body = id_body.text.toString()
                val types = type
                val date = getDate()
                val note = Notes(null, head,body,date,types)
                Log.d("tag",note.toString())
                viewModel.insert(note)
                Toast.makeText(activity,"Saved", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
            }else{
                Snackbar.make(view,"You have to Enter Something!", Snackbar.LENGTH_LONG).apply {
                    show()
                }
            }

        }
    }
    fun getDate():String{
        val c = Calendar.getInstance()
        val date = c.get(Calendar.DATE)
        val month = c.get(Calendar.MONTH)+1
        val year = c.get(Calendar.YEAR)

        return "$date/$month/$year"
    }
}