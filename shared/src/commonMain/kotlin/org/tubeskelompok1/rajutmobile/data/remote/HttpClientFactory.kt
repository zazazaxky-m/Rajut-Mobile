package org.tubeskelompok1.rajutmobile.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.tubeskelompok1.rajutmobile.data.local.AppPreferences

object HttpClientFactory {
    fun create(engine: HttpClientEngine, preferences: AppPreferences): HttpClient = HttpClient(engine) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true; isLenient = true; explicitNulls = false })
        }
        install(Logging) {
            level = LogLevel.INFO
            logger = object : Logger { override fun log(message: String) = println("Arajut API: $message") }
            sanitizeHeader { it == HttpHeaders.Authorization }
        }
        install(Auth) {
            bearer {
                loadTokens {
                    preferences.getToken().takeIf { it.isNotBlank() }?.let { BearerTokens(it, "") }
                }
                sendWithoutRequest { true }
            }
        }
        install(DefaultRequest) {
            url(apiBaseUrl())
            contentType(ContentType.Application.Json)
        }
        HttpResponseValidator {
            handleResponseExceptionWithRequest { cause, _ ->
                val responseException = cause as? ResponseException ?: throw cause
                val message = runCatching {
                    responseException.response.body<ApiErrorDto>().error
                }.getOrNull()
                throw IllegalStateException(message ?: "Permintaan ke server gagal")
            }
        }
    }
}

expect fun apiBaseUrl(): String
