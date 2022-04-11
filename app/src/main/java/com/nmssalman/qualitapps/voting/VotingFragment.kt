package com.nmssalman.qualitapps.voting

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.nmssalman.qualitapps.R
import com.nmssalman.qualitapps.Utilities.DialogBuilder
import com.nmssalman.qualitapps.Utilities.Utils
import com.nmssalman.qualitapps.databinding.FragmentVotingBinding


class VotingFragment : Fragment(), FirebaseDatabase.FirebaseListener, VotingListAdapter.VotingClicksListener {
    private lateinit var utils: Utils
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var binding: FragmentVotingBinding
    private lateinit var adapterList: VotingListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        utils = Utils(requireActivity())
        binding = FragmentVotingBinding.bind(view)
        firebaseDatabase = FirebaseDatabase(this, requireContext())
        adapterList = VotingListAdapter(listOf(), this)
        initAdapter()
        utils.setTitleBar(utils.getString(R.string.user_list))
        firebaseDatabase.getData()

    }

    fun initAdapter(){
        with(binding.listItem) {
            val layoutManager = LinearLayoutManager(context)
            this.layoutManager = layoutManager
            adapter = adapterList
        }
    }

    override fun onReceive(list: ArrayList<Voters>) {
        adapterList.updateValue(list)
        adapterList.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_voting, container, false)

    }

    override fun decrementVote(name: String, vote: Int) {
        if(vote >= 0)
            firebaseDatabase.addVoter(name, vote)
        else
            DialogBuilder.dialogBuilder("Invalid Vote", requireContext()).show()
    }

    override fun increamentVote(name: String, vote: Int) {
        firebaseDatabase.addVoter(name, vote)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_voters, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_add_voter -> {
                addVoter()
                return true
            }
            else -> return false
        }
    }

    fun addVoter(){
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Enter Voter Name")
        val input = EditText(requireContext())
        input.setHint("Voter Name")
        input.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        builder.setView(input)
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            firebaseDatabase.addVoter(input.text.toString(), 0)
            addVoter()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }
}