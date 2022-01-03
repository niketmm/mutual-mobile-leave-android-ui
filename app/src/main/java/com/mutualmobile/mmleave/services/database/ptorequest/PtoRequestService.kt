package com.mutualmobile.mmleave.services.database.ptorequest

import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.model.User

interface PtoRequestService {
  fun makePtoRequest(
    user: User,
    ptoProperties: PtoProperties
  )

  fun approvePtoRequest(ptoProperties: PtoProperties,user: User)
}