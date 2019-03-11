package com.cristian_developer.toma_la_orden.data.net

class ResponseBody<T>(
    val success: Boolean,
    val data: T,
    val err: String?
)