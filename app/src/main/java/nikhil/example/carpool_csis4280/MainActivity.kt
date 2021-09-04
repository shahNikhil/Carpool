package nikhil.example.carpool_csis4280

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nikhil.example.carpool_csis4280.NavigationFragements.RegisterFragment
import nikhil.example.carpool_csis4280.retrofit.*
import nikhil.example.carpool_csis4280.retrofit.LocalDB.AppDatabase
import nikhil.example.carpool_csis4280.retrofit.LocalDB.CarpoolViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var gso: GoogleSignInOptions
    lateinit var viewModel: CarpoolViewModel
    lateinit var googleSignInClient: GoogleSignInClient
    val RC_SIGN_IN: Int = 123
    val TAG: String = "Google Sign In"
    private lateinit var auth: FirebaseAuth
    companion object{
        const val MY_URL ="http://54.166.232.143:5000/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(CarpoolViewModel::class.java)

        auth = Firebase.auth


        val signInBtn = findViewById<SignInButton>(R.id.signInBtn)

        signInBtn.setOnClickListener {
            loadProfile()

        }

         gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent

        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun updateUI(user: FirebaseUser) {

        if (user != null) {
            Toast.makeText(this, user.email, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show()
        }


    }
    private fun loadProfile() {
        val retrofit = Retrofit.Builder().baseUrl(MY_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
        val service = retrofit.create(BackendAPI::class.java)


        service.getAllUser().enqueue(object : Callback<List<UserList>> {
            override fun onResponse(
                call: Call<List<UserList>>,
                response: Response<List<UserList>>
            ) {
                Log.d("test", "onResponse : ${response.body().toString()}")
                viewModel.insertListOfUsers(response.body()!!)
            }

            override fun onFailure(call: Call<List<UserList>>, t: Throwable) {
                Log.d("test", "onFailure")
            }

        })
        service.getAllLocation().enqueue(object : Callback<List<LocationList>> {
            override fun onResponse(
                call: Call<List<LocationList>>,
                response: Response<List<LocationList>>
            ) {
                Log.d("test", "onResponse : ${response.body().toString()}")
                viewModel.insertListOfLocation(response.body()!!)
            }

            override fun onFailure(call: Call<List<LocationList>>, t: Throwable) {
                Log.d("test", "onFailure")
            }

        })


        service.getAllRequest().enqueue(object : Callback<List<ConfirmRequestList>> {
            override fun onResponse(
                call: Call<List<ConfirmRequestList>>,
                response: Response<List<ConfirmRequestList>>
            ) {
                Log.d("test", "onResponse : ${response.body().toString()}")
                viewModel.insertListOfRequest(response.body()!!)
            }

            override fun onFailure(call: Call<List<ConfirmRequestList>>, t: Throwable) {
                Log.d("test", "onFailure")
            }

        })

        service.getAllRideDetails().enqueue(object : Callback<List<RideList>> {
            override fun onResponse(
                call: Call<List<RideList>>,
                response: Response<List<RideList>>
            ) {
                Log.d("test", "onResponse : ${response.body().toString()}")
                viewModel.insertListOfRides(response.body()!!)
            }

            override fun onFailure(call: Call<List<RideList>>, t: Throwable) {
                Log.d("test", "onFailure")
            }

        })
        signIn()

    }

}