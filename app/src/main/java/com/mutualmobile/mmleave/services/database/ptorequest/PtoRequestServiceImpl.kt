package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.di.FirebaseModule
import com.mutualmobile.mmleave.data.model.PtoRequestDomain
import com.mutualmobile.mmleave.data.ui_event.PtoRequestEvents
import com.mutualmobile.mmleave.services.auth.firebase.await
import kotlin.collections.HashMap
import com.mutualmobile.mmleave.util.Constants.PTO_LIST_COLLECTION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.sql.Date
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PtoRequestServiceImpl @Inject constructor() : PtoRequestService {

    val TAG = PtoRequestServiceImpl::class.simpleName

    override suspend fun makePtoRequest(
        ptoRequests: List<PtoRequestDomain?>,
        selectedAdmins: List<String?>
    ) = flow {

        emit(PtoRequestEvents.Loading)

        ptoRequests.forEach {
            FirebaseModule.provideUserPtoRequestDocReference()
                .document(it?.date.toString())
                .set(getPtoMap(ptoRequest = it, selectedAdmins = selectedAdmins))
                .await()
        }

        emit(PtoRequestEvents.Success)


    }.catch {
        emit(PtoRequestEvents.Failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


//    = callbackFlow {
//        val deferredFirebaseResponse = CoroutineScope(Dispatchers.IO).async {
//            try {
//                ptoRequests.forEach {
//                    FirebaseModule.provideUserPtoRequestDocReference()
//                        .document(it?.date.toString())
//                        .set(getPtoMap(ptoRequest = it, selectedAdmins = selectedAdmins))
//                }
//            }
//            catch (e : Exception){
//                trySend(
//                    PtoRequestEvents.Failed(e.message ?: "Couldn't able to save the info")
//                )
//            }
//        }
//
//        if (deferredFirebaseResponse.await())
//
//        awaitClose {
//            deferredFirebaseResponse.isCancelled
//        }
//    }

    override suspend fun updateUserPtoDetails(leaveLeft : Int) {
        val updateUser = HashMap<String,Any>()
        updateUser["leaveLeft"] = leaveLeft

        FirebaseAuth.getInstance().currentUser?.email?.let { currentUserEmail ->
            FirebaseModule.provideFirebaseUserCollectionReference()
                .document(currentUserEmail)
                .update(updateUser)
        }
    }

    private fun getPtoMap(
        ptoRequest: PtoRequestDomain?,
        selectedAdmins: List<String?>
    ): HashMap<String, Any?> {
        val ptoMap = HashMap<String, Any?>()
        ptoMap["description"] = ptoRequest?.description
        ptoMap["email"] = ptoRequest?.email
        ptoMap["date"] = ptoRequest?.date?.toFirebaseTimestamp()
        ptoMap["ptoStatus"] = ptoRequest?.status
        ptoMap["selectedAdmins"] = selectedAdmins
        return ptoMap
    }

    override suspend fun approvePtoRequest(
        ptoRequestDomain: PtoRequestDomain
    ) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val ptoListCollectionRef = FirebaseFirestore.getInstance().collection(PTO_LIST_COLLECTION)

        firebaseUser?.email?.let { email ->
            ptoListCollectionRef.whereArrayContains("approver", email).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }
    }
}

fun LocalDate.toFirebaseTimestamp(): Timestamp {
    return Timestamp(
        Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
    )
}