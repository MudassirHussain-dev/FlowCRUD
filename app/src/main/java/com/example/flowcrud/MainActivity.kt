package com.example.flowcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flowcrud.data.adapter.AllUserAdapter
import com.example.flowcrud.data.model.User
import com.example.flowcrud.data.network.apis.MyListener
import com.example.flowcrud.databinding.ActivityMainBinding
import com.example.flowcrud.databinding.AlertDialogLayoutBinding
import com.example.flowcrud.mvvm.MainViewModel
import com.example.flowcrud.utils.Toasti
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MyListener {

    private val TAG = "main"
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val mainViewModel: MainViewModel by viewModels()

    // @Inject
    lateinit var allUserAdapter: AllUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        BackgroundAddUserTask()
        inItRecyclerView()
        BackgroundGetAllUser()

        binding.apply {
            btnPreference.setOnClickListener {

                lifecycleScope.launchWhenCreated {
                    mainViewModel.protoReadToLocal.collect {
                        Toasti(it.username + "\n" + it.email + "\n" + it.id)
                    }
                    /* mainViewModel.readToLocal.collect {
                         Toasti(it)
                     }*/
                }
            }
        }


    }

    private fun inItRecyclerView() {
        allUserAdapter = AllUserAdapter(this)
        binding.apply {
            rvAllUser.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = allUserAdapter
            }
        }
    }

    private fun BackgroundGetAllUser() {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                mainViewModel.getAllUser()
                    .onStart {
                        progressBar.isVisible = true
                        rvAllUser.isVisible = false
                    }
                    .catch { e ->
                        progressBar.isVisible = false
                        rvAllUser.isVisible = false
                        Log.d(TAG, "BackgroundGetAllUser:${e.message} ")
                    }.collect {
                        if (it.isSuccessful) {
                            progressBar.isVisible = false
                            rvAllUser.isVisible = true
                            allUserAdapter.submitList(it.body()?.users)
                        } else {
                            Toasti("Data not found ")
                        }
                    }
            }
        }
    }

    private fun BackgroundAddUserTask() {
        binding.btnSaveData.setOnClickListener {
            binding.apply {
                lifecycleScope.launchWhenStarted {

                    mainViewModel.registerUser(
                        etUserName.text.toString(),
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    ).catch { e ->
                        Log.d(TAG, "onCreate: ${e.message}")
                    }.collect {
                        if (it.isSuccessful) {
                            mainViewModel.writeToLocal(etUserName.text.toString())
                            mainViewModel.protoWriteToLocal(
                                etUserName.text.toString(),
                                etEmail.text.toString(),
                                1
                            )
                            val result = it.body()
                            if (result?.error == "200") {
                                Toasti(result.message)
                                BackgroundGetAllUser()
                            } else {
                                Toasti(result!!.message)
                            }
                        } else {
                            Toasti("data not submitted please try again later.......")
                        }

                    }


                }
            }

        }

    }

    override fun onClickData(position: Int, id: Int) {
        lifecycleScope.launchWhenStarted {
            mainViewModel.userDelete(id)
                .catch { e ->
                    Log.d(TAG, "onClickData:${e.message} ")
                }.collect {
                    if (it.isSuccessful) {
                        val result = it.body()
                        if (result?.error == "200") {
                            Toasti(result.message)
                            BackgroundGetAllUser()
                        } else {
                            Toasti(result!!.message)
                        }
                    } else {
                        Toasti("something went wrongs... ")
                    }
                }
        }
    }

    override fun onClickUpdate(
        position: Int,
        id: Int,
        username: String,
        email: String,
        password: String
    ) {
        val alertDialog = AlertDialog.Builder(this@MainActivity)
        val binding = AlertDialogLayoutBinding.inflate(LayoutInflater.from(this@MainActivity))
        val dialog = alertDialog.create()
        dialog.setView(binding.root)
        binding.apply {
            etUserName.setText(username)
            //  etPassword.setText(password)
            etEmail.setText(email)
            btnUpdate.setOnClickListener {
                if (!TextUtils.isEmpty(etUserName.text.toString()) && !TextUtils.isEmpty(etEmail.text.toString()) && !TextUtils.isEmpty(
                        etPassword.text.toString()
                    )
                ) {
                    lifecycleScope.launchWhenStarted {
                        mainViewModel.userUpdate(
                            id,
                            etUserName.text.toString(),
                            etEmail.text.toString(),
                            etPassword.text.toString()

                        )
                            .catch { e ->
                                Log.d(TAG, "onClickUpdate: ${e.message} ")
                            }.collect {
                                if (it.isSuccessful) {
                                    val result = it.body()
                                    if (result?.error == "200") {
                                        Toasti(result.message)
                                        BackgroundGetAllUser()
                                        dialog.dismiss()
                                    } else {
                                        Toasti(result!!.message)

                                    }

                                } else {
                                    Toasti("something went wrongs....")
                                }
                            }
                    }
                } else {
                    Toasti("please fill the filled....")
                }
            }
        }
        dialog.show()
    }
}