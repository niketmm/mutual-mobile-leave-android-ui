package com.mutualmobile.mmleave.feature_pto.presentation

import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.data.model.PtoRequestDomain
import java.time.LocalDate

sealed class ApplyPtoEvent{
    data class SearchUser(val username : String) : ApplyPtoEvent()
    data class SelectAdmins(val selectedAdminList : List<MMUser?>) : ApplyPtoEvent()
    data class SelectPtoDates(val selectedPtoDates : List<LocalDate>) : ApplyPtoEvent()
    data class AddDescription(val desc : String) : ApplyPtoEvent()
    object ApplyForPto : ApplyPtoEvent()
}