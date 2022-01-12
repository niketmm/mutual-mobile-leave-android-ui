package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import androidx.navigation.Navigator
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.firestore.PtoRequest
import com.mutualmobile.mmleave.firestore.PtoRequestDateModel
import com.mutualmobile.mmleave.firestore.SetGetPtoRequests
import com.mutualmobile.mmleave.services.auth.firebase.await
import com.mutualmobile.mmleave.util.Constants.PTO_LIST_COLLECTION
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.callbackFlow
import java.sql.Date
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
class PtoRequestServiceImpl @Inject constructor(
    @Named("firebaseUserCollectionReference")
    private val firebaseUserCollectionReference: CollectionReference,
    @Named("firebasePtoRequestCollectionReference")
    private val firebasePtoRequestCollectionReference: CollectionReference
) : PtoRequestService {

    val TAG = PtoRequestServiceImpl::class.simpleName

    override suspend fun makePtoRequest(
        ptoRequest: List<SetGetPtoRequests>
    ): Boolean {
        ptoRequest.forEach {
            firebasePtoRequestCollectionReference
                .document(it.date.toString())
                .set(getPtoMap(ptoRequest = it))
        }
        return true
    }

    private fun getPtoMap(ptoRequest: SetGetPtoRequests): HashMap<String, Any?> {
        val ptoMap = HashMap<String, Any?>()
        ptoMap["description"] = ptoRequest.description
        ptoMap["email"] = ptoRequest.email
        ptoMap["date"] = ptoRequest.date?.toFirebaseTimestamp()
        ptoMap["ptoStatus"] = ptoRequest.status
        return ptoMap
    }

    private suspend fun filterPtoRequests(newPtoList: List<Timestamp>): List<FirebasePtoRequest> {
        val duplicatePtoRequestList = mutableListOf<FirebasePtoRequest>()
        FirebaseAuth.getInstance().currentUser?.email?.let { safeEmail ->
            val userPtoListCollectionRef = getAllPtoRequestsCollection(safeEmail)
            val documents =
                userPtoListCollectionRef.whereArrayContainsAny("pto_list", newPtoList).get()
                    .await()
                    .documents
            documents.forEach { document ->
                document.toObject<FirebasePtoRequest>()?.let {
                    duplicatePtoRequestList.add(it)
                    it.pto_list?.size
                }
            }
        }
        return duplicatePtoRequestList
    }

    suspend fun getAllPtoRequestsList(): List<FirebasePtoRequest> {
        val allPtosList = mutableListOf<FirebasePtoRequest>()
        FirebaseAuth.getInstance().currentUser?.email?.let { safeEmail ->
            val userPtoListCollectionRef = getAllPtoRequestsCollection(safeEmail)
            val documents = userPtoListCollectionRef.get()
                .await()
                .documents
            documents.forEach { document ->
                document.toObject<FirebasePtoRequest>()?.let {
                    allPtosList.add(it)
                    it.pto_list?.size
                }
            }
        }
        return allPtosList
    }

    private fun getAllPtoRequestsCollection(safeEmail: String): CollectionReference {
        return firebaseUserCollectionReference
            .document(safeEmail)
            .collection(PTO_LIST_COLLECTION)
    }

    override suspend fun approvePtoRequest(
        ptoRequest: SetGetPtoRequests
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

    override suspend fun fetchAdminList() = callbackFlow {
        val listenerRegistration = FirebaseFirestore
            .getInstance()
            .collection("users_list")
            .whereEqualTo("userType", 1).addSnapshotListener { admins, error ->
                admins?.documents?.map { doc -> doc.toObject(MMUser::class.java) }?.let {
                    Log.d(TAG, "fetchAdminList: ${it.toString()}")
                    trySend(it).onFailure {
                    }
                }
            }
        awaitClose {
            listenerRegistration.remove()
        }
    }

    override suspend fun fetchUsersByUsername(username: String) = callbackFlow {
        val listenerRegistration = FirebaseFirestore.getInstance()
            .collection("users_list")
            .whereArrayContains("nameAsArray", username)
            .addSnapshotListener { filteredAdmins, error ->
                filteredAdmins?.let {
                    filteredAdmins.documents.map { doc ->
                        doc.toObject(MMUser::class.java)
                    }.let {
                        Log.d(TAG, "fetchUsersByUsername: $it")
                        trySend(it).onFailure {
                        }
                    }
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }
}

fun LocalDate.toFirebaseTimestamp(): Timestamp {
    return Timestamp(
        Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
    )
}

//fun makePtoRequestOld(ptoRequest: PtoRequest) {
//    val duplicatePtoList =
//        ptoRequest.ptoList
//    ptoRequest.ptoList?.toFirebaseTimestamp()?.let { filterPtoRequests(it) }
//    if (duplicatePtoList?.isEmpty() == true) {
//        ptoRequest.ptoList?.forEach {
//            firebasePtoRequestCollectionReference
//                .document(it.toString())
//                .set(getPtoMap(ptoRequest = ptoRequest))
//        }
//    } else {
//        // Updating if duplicate Exists
//        ptoRequest.ptoList?.forEach {
//            firebasePtoRequestCollectionReference
//                .document(it.toString())
//                .update(getPtoMap(ptoRequest = ptoRequest))
//        }
//    }
//}