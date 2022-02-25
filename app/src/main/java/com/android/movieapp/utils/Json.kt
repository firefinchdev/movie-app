package com.android.movieapp.utils

import kotlinx.serialization.json.Json

val json = Json {
    this.ignoreUnknownKeys = true
}