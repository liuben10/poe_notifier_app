package com.example.benja.poebrowser

interface SQLContainer {

    fun create(): String

    fun dropTable(): String

    fun extraSql(): Map<String, String>

}
