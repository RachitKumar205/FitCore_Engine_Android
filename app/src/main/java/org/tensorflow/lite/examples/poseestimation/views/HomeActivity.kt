package org.tensorflow.lite.examples.poseestimation.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import org.tensorflow.lite.examples.poseestimation.MainActivity
import org.tensorflow.lite.examples.poseestimation.R
import org.tensorflow.lite.examples.poseestimation.utils.Extensions.toast
import org.tensorflow.lite.examples.poseestimation.utils.FirebaseUtils.firebaseAuth

class HomeActivity : AppCompatActivity(), MyAdapter.OnItemClickListener{

    private lateinit var dbref : DatabaseReference
    private lateinit var poseRecyclerView : RecyclerView
    private lateinit var poseArrayList : ArrayList<Pose>

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        supportActionBar?.hide(); //hide the title bar
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        poseRecyclerView = findViewById(R.id.poseList)
        poseRecyclerView.layoutManager = LinearLayoutManager(this)
        poseRecyclerView.setHasFixedSize(true)
        poseArrayList = arrayListOf<Pose>()
        getPoseData()

        btnSignOut.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, CreateAccountActivity::class.java))
            toast("signed out")
            finish()
        }
    }

    override fun onItemClick(position: Int) {
        startActivity(Intent(this, MainActivity::class.java))
        val clickedItem = poseArrayList[position]
    }

    private fun getPoseData() {

        dbref = FirebaseDatabase.getInstance("https://fitframe-6da33-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Poses")
        dbref.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (poseSnapshot in snapshot.children){

                        val pose = poseSnapshot.getValue(Pose::class.java)
                        poseArrayList.add(pose!!)

                    }

                    poseRecyclerView.adapter = MyAdapter(poseArrayList, this@HomeActivity)

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
}



