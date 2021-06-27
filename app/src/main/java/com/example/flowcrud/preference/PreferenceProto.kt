package com.example.flowcrud.preference

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.flowcrud.LoginUser
import com.example.flowcrud.data.model.User2
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class PreferenceProto @Inject constructor(@ApplicationContext val context: Context) {
    private val Context.datastore: DataStore<LoginUser> by dataStore(
        fileName = "LoginUser",
        serializer = SerializerProto
    )

    suspend fun protoWriteToLocal(username: String, email: String, id: Int) =
        context.datastore.updateData {
            it.toBuilder()
                .setUsername(username)
                .setEmail(email)
                .setId(id)
                .build()
        }

    val protoReadToLocal: Flow<User2> = context.datastore.data
        .catch {
            if (this is Exception) {
                Log.d("pref", "${this.message} ")
            }
        }.map {
            val user2 = User2(it.username, it.email, it.id)
            user2
        }
}