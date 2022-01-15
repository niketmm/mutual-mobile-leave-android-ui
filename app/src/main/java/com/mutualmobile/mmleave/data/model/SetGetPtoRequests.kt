package com.mutualmobile.mmleave.data.model

import java.time.LocalDate

data class SetGetPtoRequests(
    var email: String? = null,
    var description: String? = "",
    var date : LocalDate,
    var adminList : List<MMUser?> = emptyList(),
    var status: PtoRequestDateModel.PtoGraphStatus = PtoRequestDateModel.PtoGraphStatus.APPLIED
)