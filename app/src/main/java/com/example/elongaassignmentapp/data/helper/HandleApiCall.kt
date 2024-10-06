package com.example.elongaassignmentapp.data.helper

import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.net.UnknownHostException
import com.example.elongaassignmentapp.domain.model.Result

/**
 * Executes a given suspendable API call and safely handles various exceptions.
 *
 * The method wraps the result in a `Result<T>`, where:
 * - Success: The call completes successfully and the response is wrapped in `Result.Success(data)`.
 * - HttpException: The call results in a client or server error, with appropriate HTTP status code handling.
 * - UnknownHostException: The call fails due to network issues, returning `Result.NetworkError`.
 * - CancellationException: The operation is cancelled, returning `Result.Canceled`.
 * - Other exceptions: Any other exception results in `Result.OtherError(e)` for general error handling.
 *
 * @param call A suspendable lambda representing the API call to execute.
 * @return A `Result<T>` representing the outcome of the call, whether it succeeds or fails.
 */
suspend fun <T : Any?> handleApiCall(call: suspend () -> T): Result<T> {
    return try {
        val data = call()
        Result.Success(data)
    } catch (e: HttpException) {
        val code = e.code()

        when (code) {
            400 -> Result.HttpError.ClientError.BadRequest(code = code)
            401 -> Result.HttpError.ClientError.Unauthorized(code = code)
            403 -> Result.HttpError.ClientError.Forbidden(code = code)
            404 -> Result.HttpError.ClientError.NotFound(code = code)
            409 -> Result.HttpError.ClientError.Conflict(code = code)
            429 -> Result.HttpError.ClientError.TooManyRequests(code = code)
            in 400..499 -> Result.HttpError.ClientError.Other(code = code)
            in 500..599 -> Result.HttpError.ServerError(code = code)
            else -> Result.HttpError.Other(code = code)
        }
    } catch (_: UnknownHostException) {
        Result.NetworkError
    } catch (_: CancellationException) {
        Result.Canceled
    } catch (e: Exception) {
        Result.OtherError(e)
    }
}
