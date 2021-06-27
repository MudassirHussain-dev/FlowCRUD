package com.example.flowcrud.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowcrud.data.repository.MyRepository
import com.example.flowcrud.data.response.ResponseRegister
import com.example.flowcrud.preference.Preference
import com.example.flowcrud.preference.PreferenceProto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val myRepository: MyRepository,
    private val preference: Preference,
    private val preferenceProto: PreferenceProto
) : ViewModel() {

    /*   private val _registerResponse: MutableLiveData<Response<ResponseRegister>> = MutableLiveData()
       val registerResponse: LiveData<Response<ResponseRegister>> = _registerResponse*/
    fun registerUser(username: String, email: String, password: String) = //viewModelScope.launch {
        myRepository.registerUser(username, email, password)
    // }

    fun getAllUser() = myRepository.getAllUser()
    fun userDelete(id: Int) = myRepository.userDelete(id)
    fun userUpdate(id: Int, username: String, email: String, password: String) =
        myRepository.userUpdate(id, username, email, password)


    //using preference
    fun writeToLocal(username: String) = viewModelScope.launch {
        preference.writeToLocal(username)
    }

    val readToLocal = preference.readToLocal

    fun protoWriteToLocal(username: String, email: String, id: Int) = viewModelScope.launch {
        preferenceProto.protoWriteToLocal(username, email, id)
    }

    val protoReadToLocal = preferenceProto.protoReadToLocal


}