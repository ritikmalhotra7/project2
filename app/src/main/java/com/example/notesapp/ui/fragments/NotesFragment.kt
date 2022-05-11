package com.example.notesapp.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.R
import com.example.notesapp.database.AppDatabase
import com.example.notesapp.database.AppRepository
import com.example.notesapp.model.Notes
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.viewmodel.NotesViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_add_note.id_heading
import kotlinx.android.synthetic.main.notes_fragment.*

class NotesFragment : Fragment(R.layout.notes_fragment) {
    private lateinit var viewModel: NotesViewModel
    val args : NotesFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.notes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository  = AppRepository(AppDatabase(requireActivity()))
        val viewModelFactory = NotesViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(NotesViewModel::class.java)

        val note = args.notes

        idheading.text = note.heading
        iddate.text = note.date
        idbody.text = note.body
        idtype.text = note.type

        back.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_mainFragment)
        }

        idiv.setOnClickListener {
            switcher.showNext()
            switcheriv.showNext()
            switcher1.showNext()
            switcher2.showNext()

            heading.text = idheading.text as Editable
        }
        id_ive.setOnClickListener {
            switcher.showNext()
            switcheriv.showNext()
            switcher1.showNext()
            switcher2.showNext()
            idbody.text = idebody.text
            idheading.text = heading.text
            var t = resources.getStringArray(R.array.type)[0]
            id_stype.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                    t = resources.getStringArray(R.array.type)[position]
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            } )
            idtype.text = t
            val head = idheading.text.toString()
            val body = idbody.text.toString()
            val types = t
            viewModel.update(Notes(note.id,head,body,note.date,types))
        }
    }


}