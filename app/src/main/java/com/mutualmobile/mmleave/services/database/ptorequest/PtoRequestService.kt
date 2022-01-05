package com.mutualmobile.mmleave.services.database.ptorequest

import android.provider.ContactsContract.CommonDataKinds.Email
import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.model.User

interface PtoRequestService {
  suspend fun makePtoRequest(
    ptoProperties: PtoProperties
  )
  suspend fun approvePtoRequest(ptoProperties: PtoProperties)
}