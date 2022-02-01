package com.mutualmobile.mmleave.feature_pto.domain.model

import com.mutualmobile.mmleave.feature_pto.domain.model.MMUser
import com.mutualmobile.mmleave.feature_pto.domain.model.PtoRequestDateModel
import java.time.LocalDate

data class PtoRequestDomain(
    var email: String? = null,
    var description: String? = "",
    var date : LocalDate,
    var adminList : List<MMUser?> = emptyList(),
    var status: PtoRequestDateModel.PtoGraphStatus = PtoRequestDateModel.PtoGraphStatus.APPLIED
)