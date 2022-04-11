package com.nmssalman.qualitapps.voting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nmssalman.qualitapps.databinding.ItemUsersListBinding
import com.nmssalman.qualitapps.databinding.ItemVotingBinding
import com.nmssalman.qualitapps.userslist.UsersListAdapter
import com.nmssalman.qualitapps.userslist.dataset.User

class VotingListAdapter(private var voters: List<Voters>, private val listener: VotingClicksListener): RecyclerView.Adapter<VotingListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemVotingBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat", "DefaultLocale")
        fun bind(voter: Voters, listener: VotingClicksListener) {
            with(binding) {
                name.text = voter.name
                vote.text = voter.vote.toString()
                incrementVote.setOnClickListener {
                    listener.increamentVote(voter.name, voter.vote + 1)
                }
                decrementVote.setOnClickListener {
                    listener.decrementVote(voter.name, voter.vote - 1)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemVotingBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = voters[position]
        holder.bind(item, listener)
    }

    override fun getItemCount(): Int {
        return voters.count()
    }

    fun updateValue(list: ArrayList<Voters>)
    {
        this.voters = list
    }

    interface VotingClicksListener{
        fun increamentVote(name: String, vote: Int)
        fun decrementVote(name: String, vote: Int)
    }
}