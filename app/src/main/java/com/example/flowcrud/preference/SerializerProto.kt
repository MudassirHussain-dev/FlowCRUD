package com.example.flowcrud.preference

import androidx.datastore.core.Serializer
import com.example.flowcrud.LoginUser
import java.io.InputStream
import java.io.OutputStream

object SerializerProto : Serializer<LoginUser> {
    override val defaultValue: LoginUser
        get() = LoginUser.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): LoginUser {
        return LoginUser.parseFrom(input)
    }

    override suspend fun writeTo(t: LoginUser, output: OutputStream) {
        return t.writeTo(output)
    }
}