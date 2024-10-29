package com.example.newsreaderdemo.domain.model

/**
 * A generic class that holds a request result and its data.
 * @param <T>
 */
sealed class RequestError : Throwable() {

    object NetworkError : RequestError()

    sealed class HttpError : RequestError() {
        abstract val code: Int

        sealed class ClientError : HttpError() {
            data class BadRequest(override val code: Int = 400) : ClientError()
            data class Unauthorized(override val code: Int = 401) : ClientError()
            data class Forbidden(override val code: Int = 403) : ClientError()
            data class NotFound(override val code: Int = 404) : ClientError()
            data class Conflict(override val code: Int = 409) : ClientError()
            data class TooManyRequests(override val code: Int = 429) : ClientError()
            data class Other(override val code: Int) : ClientError()
        }
        data class ServerError(override val code: Int) : HttpError()
        data class Other(override val code: Int) : HttpError()
    }

    object Canceled : RequestError()
    data class OtherError(val exception: Exception? = null) : RequestError()
}
