package com.example.notesapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.adapters.AppRVAdapter
import com.example.notesapp.database.AppDatabase
import com.example.notesapp.database.AppRepository
import com.example.notesapp.model.Notes
import com.example.notesapp.viewmodel.NotesViewModel
import com.example.notesapp.viewmodel.NotesViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var rvAdapter: AppRVAdapter
    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository  = AppRepository(AppDatabase(requireActivity()))
        val viewModelFactory = NotesViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(NotesViewModel::class.java)
        viewModel.getAll().observe(viewLifecycleOwner, Observer{
            setupRV(it)
        })
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment)
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    private fun setupRV(ls :List<Notes>){
        rvAdapter = AppRVAdapter(ls)

        id_rv.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        rvAdapter.notifyDataSetChanged()

        rvAdapter.setOnItemClickListener {
            val bundle = Bundle().apply{
                putSerializable("notes",it)
            }
            Log.d("taget","taget")
            findNavController().navigate(R.id.action_mainFragment_to_notesFragment,bundle)
        }
        var itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = rvAdapter.ls.get(position)
                viewModel.delete(note)
                Snackbar.make(view!!,"Note Deleted!", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.insert(note)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(id_rv)
        }

    }

}