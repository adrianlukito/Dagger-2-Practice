package com.example.daggerpractice.ui.auth

data class AuthResource<out T>(val status: Status, val data: T?, val error: Throwable?) {
    companion object {
        fun <T> authenticated(data: T?): AuthResource<T> {
            return AuthResource(
                Status.AUTHENTICATED,
                data,
                null
            )
        }

        fun <T> error(error: Throwable, data: T?): AuthResource<T> {
            return AuthResource(
                Status.ERROR,
                data,
                error
            )
        }

        fun <T> loading(data: T?): AuthResource<T> {
            return AuthResource(
                Status.LOADING,
                data,
                null
            )
        }

        fun <T> logout(): AuthResource<T> {
            return AuthResource(
                Status.NOT_AUTHENTICATED,
                null,
                null
            )
        }
    }

    enum class Status {
        AUTHENTICATED,
        ERROR,
        LOADING,
        NOT_AUTHENTICATED
    }
}