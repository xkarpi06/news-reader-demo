package com.example.elongaassignmentapp.data.extension

import com.example.elongaassignmentapp.domain.model.RequestError
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.net.UnknownHostException

fun Exception.toCallError(): RequestError = when (this) {
    is HttpException -> {
        val code = this.code()

        when (code) {
            400 -> RequestError.HttpError.ClientError.BadRequest(code = code)
            401 -> RequestError.HttpError.ClientError.Unauthorized(code = code)
            403 -> RequestError.HttpError.ClientError.Forbidden(code = code)
            404 -> RequestError.HttpError.ClientError.NotFound(code = code)
            409 -> RequestError.HttpError.ClientError.Conflict(code = code)
            429 -> RequestError.HttpError.ClientError.TooManyRequests(code = code)
            in 400..499 -> RequestError.HttpError.ClientError.Other(code = code)
            in 500..599 -> RequestError.HttpError.ServerError(code = code)
            else -> RequestError.HttpError.Other(code = code)
        }
    }

    is UnknownHostException -> RequestError.NetworkError
    is CancellationException -> RequestError.Canceled
    else -> RequestError.OtherError(this)
}
