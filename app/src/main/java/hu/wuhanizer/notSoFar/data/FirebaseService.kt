package hu.wuhanizer.notSoFar.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import hu.wuhanizer.notSoFar.model.*
import java.util.*
import kotlin.math.tan


fun getUserId(): String
    {
        return FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }

    fun addToPending(): Task<PendingUser>
    {

        val user=PendingUser(name = "",id= getUserId(),date = Date())
        val ref= FirebaseFirestore.getInstance().collection("pending-users").document(getUserId()).set(user).continueWith{ task->

            user
        }

        return ref
    }

    fun getMyPending(): Task<PendingUser?> {
        val ref= FirebaseFirestore.getInstance().collection("pending-users").document(getUserId()).get().continueWith{ task->

            var ret:PendingUser?=null

            if(task.isSuccessful)
            {
                ret=task.result!!.toObject(PendingUser::class.java)

            }

            ret
        }

        return ref
    }

    fun updatePending(user:PendingUser): Task<Void> {
        val ref= FirebaseFirestore.getInstance().collection("pending-users").document(user.id).set(user)

        return ref
    }

    fun removeFromPending(): Task<Void> {
        val ref= FirebaseFirestore.getInstance().collection("pending-users").document(getUserId()).delete()

        return ref
    }

    fun removePending(user:PendingUser): Task<Void> {
        val ref= FirebaseFirestore.getInstance().collection("pending-users").document(user.id).delete()

        return ref
    }

    fun getPending(): Task<PendingUser?> {

        val ref= FirebaseFirestore.getInstance().collection("pending-users").orderBy("date",Query.Direction.ASCENDING)

        return ref.get().continueWith{ task ->

            var ret:PendingUser?=null

            if (task.isSuccessful) {

                var i=0
                while(i<task.result?.documents?.size!! && task.result?.documents?.get(i)?.id!! == getUserId() && task.result?.documents?.get(i)?.toObject(PendingUser::class.java)!!.room.trim().length!=0)
                {
                    i++
                }

                if(i<task.result?.documents?.size!!)
                {
                    ret= task.result?.documents?.get(i)?.toObject(PendingUser::class.java)
                }
            }

            ret
        }
    }

    fun createRoom(opponent:PendingUser): Task<Room>
    {
        val uniqueId = UUID.randomUUID().toString();

        val room=Room(id = uniqueId, date = Date(),startUserId = getUserId(),secondUserId = opponent.id,startWord = getStartWord())

        val ref= FirebaseFirestore.getInstance().collection("rooms").document(uniqueId).set(room).continueWith{ task ->

            room

        }


        return ref
    }

    fun getRoom(roomId:String):Task<Room>
    {
        return FirebaseFirestore.getInstance().collection("rooms").document(roomId).get().continueWith { task->

            var room:Room?=null

            if(task.isSuccessful)
            {
                room=task.result!!.toObject(Room::class.java)
            }

            room
        }
    }

    fun getWordQuery(room:Room): Query {

        val querySearch = FirebaseFirestore.getInstance().collection("rooms").document(room.id).collection("words").orderBy("date")

        return querySearch
    }

    fun startRoom(room: Room): Task<Void>? {

        if(room.startUserId== getUserId())
        {
            return addWord(room,room.startWord)
        }

        return null
    }

    fun addWord(room:Room,word:String): Task<Void>
    {
        val w=Word(userId = getUserId(),word = word,date = Date())

        return FirebaseFirestore.getInstance().collection("rooms").document(room.id).collection("words").document().set(w)
    }

    fun rateRoom(room:Room,rating:Rating): Task<Void> {
        if(room.startUserId== getUserId())
        {
            room.user1Rating=rating
        }else
        {
            room.user2Rating=rating
        }

        return FirebaseFirestore.getInstance().collection("rooms").document(room.id).set(room)
    }

    fun setRoomSnapshotListener(room:Room,event:com.google.firebase.firestore.EventListener<DocumentSnapshot?>): ListenerRegistration
    {
        val docRef: DocumentReference =FirebaseFirestore.getInstance().collection("rooms").document(room.id)

        return docRef.addSnapshotListener(event)
    }

    val START_WORDS=arrayOf("alma","láb","pác","vád","elme","treff","mag","méh","bibi","fáj","marék","lépdel","madaram","szín","anno","terep","kenéyr","közös","teszt","tabu","szív","igaz")

    fun getStartWord():String
    {
        val index=Random().nextInt(START_WORDS.size)

        return START_WORDS[index]
    }

    fun saveUser(user:User): Task<Void>
    {
        return FirebaseFirestore.getInstance().collection("users").document(user.userId).set(user)
    }

    fun getUser(userId:String): Task<User?> {

        return FirebaseFirestore.getInstance().collection("users").document(userId).get().continueWith { task->

            var user:User?=null

            if(task.isSuccessful)
            {
                user=task.result!!.toObject(User::class.java)
            }

            user
        }
    }
