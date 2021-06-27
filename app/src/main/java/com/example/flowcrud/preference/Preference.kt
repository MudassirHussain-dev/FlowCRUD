package com.example.flowcrud.preference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class Preference @Inject constructor(@ApplicationContext val context: Context) {

    object PreferenceKey {
        val username = stringPreferencesKey("username")
    }

    val Context.datastore by preferencesDataStore("datastore")

    suspend fun writeToLocal(username: String) {
        context.datastore.edit {
            it[PreferenceKey.username] = username
        }
    }

    val readToLocal: Flow<String> = context.datastore.data
        .catch {
            if (this is Exception) {
                emit(emptyPreferences())
            }
        }.map {
            val username = it[PreferenceKey.username] ?: ""
            username
        }
}