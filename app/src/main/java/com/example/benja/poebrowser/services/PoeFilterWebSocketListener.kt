package com.example.benja.poebrowser.services

import android.util.Log
import com.example.benja.poebrowser.PoeAppContext
import com.example.benja.poebrowser.model.PoeItemFilter
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class PoeFilterWebSocketListener : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.i(this.javaClass.simpleName, "WebSocket Open Invoked!")
        val request = PoeItemFilterWSRequest(
                "354012738-366441988-345818264-397526415-374386691",
                mutableListOf(PoeItemFilter(
                        filterName = "Some Filter",
                        name = "Loreweave",
                        league = "Synthesis"
                )))
        webSocket.send(PoeAppContext.getParser().toJson(request))
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.d(this.javaClass.simpleName, "Receiving : " + String(bytes.toByteArray()))
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.d(this.javaClass.simpleName, "Closing : " + code + " / " + reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.d(this.javaClass.simpleName, "Failure : " + response)
    }
}