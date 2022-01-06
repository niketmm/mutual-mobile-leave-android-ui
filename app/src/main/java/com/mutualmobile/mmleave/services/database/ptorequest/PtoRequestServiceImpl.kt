package com.mutualmobile.mmleave.services.database.ptorequest

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mutualmobile.mmleave.data.model.MMUser
import com.mutualmobile.mmleave.firestore.PtoProperties
import com.mutualmobile.mmleave.services.auth.firebase.await
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class PtoRequestServiceImpl : PtoRequestService {

    val TAG = PtoRequestServiceImpl::class.simpleName

    override suspend fun makePtoRequest(
        ptoProperties: PtoProperties
    ) {
        val pto = HashMap<String, Any?>()
        pto["dateFrom"] = ptoProperties.dateFrom
        pto["dateTo"] = ptoProperties.dateTo
        pto["description"] = ptoProperties.description
        pto["email"] = ptoProperties.email
        FirebaseFirestore.getInstance().collection("PTOs_list").add(pto).await()
    }

    override suspend fun approvePtoRequest(
        ptoProperties: PtoProperties
    ) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userListCollectionRef = FirebaseFirestore.getInstance().collection("PTOs_list")

        firebaseUser?.email?.let { email ->
            userListCollectionRef.whereArrayContains("approver", email).get()
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

    // Todo Inject this Firebase Cursor and get collections name using Constants
    // Todo Create Extension function for SnapShotListeners

    // Is it due to emit getting Called First?
    // Firebase returning null?

    override suspend fun fetchAdminList() = callbackFlow {
        val adminList: MutableList<MMUser> = mutableListOf()
        val fetchedAdminsSnapShots = FirebaseFirestore
            .getInstance()
            .collection("users_list")
            .whereEqualTo("userType", 1) // Fetches the Admins only
//            .get()
//            .await()

        fetchedAdminsSnapShots.addSnapshotListener { admins, error ->
            admins?.let{
                admins.documents.map { doc ->
                    doc?.let {
                        adminList.add(doc.toObject(MMUser::class.java)!!)
                    }
                }
            }
        }

        trySend(adminList)

//        awaitCancellation()
    }

    override suspend fun fetchUsersByUsername(username: String) = flow {
        val filteredAdminList = mutableListOf<MMUser>()
        FirebaseFirestore.getInstance()
            .collection("users_list")
            .whereEqualTo("displayName", username)
            .addSnapshotListener { filteredAdmins, error ->
                filteredAdmins?.let {
                    filteredAdmins.documents.map { doc ->
                        filteredAdminList.add(doc.toObject(MMUser::class.java)!!)
                    }
                }
            }

        emit(filteredAdminList)

//      .debounce(300) // Making a request after 300 Seconds
//      .distinctUntilChanged() // IF the query is same don't make a request again
//      .flowOn(Dispatchers.IO) //
//      .emit(filteredList) // Emitting the Values

    }
}