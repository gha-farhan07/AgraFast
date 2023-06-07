package com.agrafast.domain

import java.lang.Exception

sealed class FetchStatus<out R> {
  object Loading : FetchStatus<Nothing>()
//  object Empty : FetchStatus<Nothing>()
  data class Success<out R>(val data: R? = null) : FetchStatus<R>()
  data class Error(val Exception: Exception) : FetchStatus<Nothing>()

}