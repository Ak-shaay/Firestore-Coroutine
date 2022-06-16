package com.example.firestorecoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firestorecoroutine.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

data class Person(
    val name:String = "",
    val age:Int=-1
)

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database=Firebase.firestore.collection("Coroutine")
            .document("Test")

        val akshay =Person("akshay", 22)

        GlobalScope.launch(Dispatchers.IO){
            delay(3000L)
            database.set(akshay).await()
            //we dont have to use onSuccessTask listener ,etc here
            val person=database.get().await().toObject(Person::class.java)
            withContext(Dispatchers.Main){
                binding.tvData.text=person.toString()
            }
        }
    }
}