package com.example.weatherapp.core.presentation.common

import androidx.annotation.Keep

/**
 * Created by Emad Mohamed Oun on 5/8/2020.
 * NEJMO
 * emad.3oon@gmail.com
 */

@Keep
class Resource<T> {
    val resourceType: ResourceType
    var data: T? = null
    var message: String? = null
        private set
    var code: Int? = null
        private set
    var success: Boolean? = null
        private set

    constructor(
        resourceType: ResourceType,
        data: T?,
        message: String?
    ) {
        this.resourceType = resourceType
        this.data = data
        this.message = message
    }

    constructor(
        resourceType: ResourceType,
        data: T?
    ) {
        this.resourceType = resourceType
        this.data = data
    }

    constructor(
        resourceType: ResourceType,
        data: T?,
        message: String?,
        statusCode: Int
    ) {
        this.resourceType = resourceType
        this.data = data
        this.message = message
        this.code = statusCode
    }

    constructor(
        resourceType: ResourceType,
        message: String?
    ) {
        this.resourceType = resourceType
        this.message = message
    }

    constructor(
        resourceType: ResourceType,
    ) {
        this.resourceType = resourceType
    }

    constructor(
        resourceType: ResourceType,
        statusCode: Int,
        message: String? = null
    ) {
        this.resourceType = resourceType
        this.code = statusCode
        this.message = message
    }

    companion object {
        fun <T> loading(): Resource<T> = Resource(ResourceType.LOADING, data = null)

        fun <T> loading(data: T): Resource<T> = Resource(ResourceType.LOADING, data)

        fun <T> loading(message: String): Resource<T> = Resource(ResourceType.LOADING, message)

        fun <T> error(data: T): Resource<T> = Resource(ResourceType.ERROR, data)

        fun <T> success(): Resource<T> = Resource(ResourceType.SUCCESS, data = null)

        fun <T> success(data: T): Resource<T> = Resource(ResourceType.SUCCESS, data)

        fun <T> success(
            data: T?,
            message: String?,
            statusCode: Int,
        ): Resource<T> = Resource(ResourceType.SUCCESS, data, message, statusCode)

        fun <T> success(
            data: T,
            message: String?
        ): Resource<T> =
            Resource(ResourceType.SUCCESS, data, message)

        fun <T> successMessage(message: String?): Resource<T> =
            Resource(ResourceType.SUCCESS, message)

        fun <T> connectionError(data: T?): Resource<T> =
            Resource(ResourceType.CONNECTION_ERROR, data = data)

        fun <T> emptyDataError(): Resource<T> = Resource(ResourceType.EMPTY_DATA, data = null)

        fun <T> unauthorizedError(message: String): Resource<T> =
            Resource(ResourceType.UNAUTHORIZED_ERROR, message = message)

        fun <T> badRequestError(message: String): Resource<T> =
            Resource(ResourceType.BAD_REQUEST_ERROR, message = message)

        fun <T> blockUserError(message: String, code: Int): Resource<T> =
            Resource(
                ResourceType.BLOCK_USER_ERROR,
                message = message,
            statusCode = code)

        fun <T> serverError(): Resource<T> =
            Resource(ResourceType.SERVER_ERROR)

        fun <T> notFoundError(message: String?): Resource<T> =
            Resource(ResourceType.NOT_FOUND, message = message)

        fun <T> notActivatedError(message: String): Resource<T> =
            Resource(ResourceType.NOT_ACTIVATED_ERROR, message = message)

        fun <T> validationError(
            statusCode: Int,
            message: String? = null
        ): Resource<T> =
            Resource(ResourceType.VALIDATION_ERROR, statusCode, message)

        fun <T> navigation(
            statusCode: Int,
            message: String? = null
        ): Resource<T> =
            Resource(ResourceType.NAVIGATION, statusCode, message)

        fun <T> unknownError(
            message: String? = null
        ): Resource<T> =
            Resource(ResourceType.UNKNOWN_ERROR, message)
    }

}