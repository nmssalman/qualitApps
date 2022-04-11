package com.nmssalman.qualitapps.userslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.nmssalman.qualitapps.Utilities.MyApplication
import com.nmssalman.qualitapps.userslist.dataset.User

class UsersViewModel(application: Application): AndroidViewModel(application) {
    private val usersRepository: UsersRepository
    val networkErrorMessage: LiveData<String>
    val getUserSuccess: LiveData<List<User>>

    init {
        usersRepository = UsersRepository(application.applicationContext, MyApplication.moshi)
        networkErrorMessage = usersRepository.networkErrorMessage
        getUserSuccess = usersRepository.getUsersSuccess
    }

    fun getUserList(){
        usersRepository.getUsersList()
    }
}