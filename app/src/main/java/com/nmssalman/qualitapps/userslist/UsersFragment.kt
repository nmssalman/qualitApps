package com.nmssalman.qualitapps.userslist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nmssalman.qualitapps.Utilities.Utils
import com.nmssalman.qualitapps.databinding.FragmentUsersBinding
import com.nmssalman.qualitapps.userslist.UsersListAdapter
import com.nmssalman.qualitapps.userslist.UsersViewModel
import com.nmssalman.qualitapps.*

class UsersFragment : Fragment() {
    lateinit var viewModel: UsersViewModel
    lateinit var binding: FragmentUsersBinding
    lateinit var adapterUsers: UsersListAdapter
    private lateinit var utils: Utils
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        utils = Utils(requireActivity())
        utils.setTitleBar(utils.getString(R.string.user_list))
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        binding = FragmentUsersBinding.bind(view)
        adapterUsers = UsersListAdapter(listOf())
        initAdapter()
        setObservers()
        viewModel.getUserList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }
    fun setObservers(){
        viewModel.networkErrorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.getUserSuccess.observe(viewLifecycleOwner, Observer {
            Log.i("AdapterCount", it.count().toString())
            adapterUsers.updateUsers(it)
            adapterUsers.notifyDataSetChanged()
        })
    }

    fun initAdapter(){
        with(binding.listItem) {
            val layoutManager = LinearLayoutManager(context)
            this.layoutManager = layoutManager
            adapter = adapterUsers
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_user_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_camera -> {
                Toast.makeText(requireContext(), "Option Menu Clicked", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return false
        }
    }
}