package nikhil.example.carpool_csis4280.NavigationFragements

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nikhil.example.carpool_csis4280.MainActivity
import nikhil.example.carpool_csis4280.R
import nikhil.example.carpool_csis4280.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var googleSignInClient: GoogleSignInClient
    var firebaseUser: FirebaseUser? = null

    lateinit var binding: FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseUser = FirebaseAuth.getInstance().currentUser
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

            googleSignInClient.signOut()

            var intent = Intent(requireContext(),MainActivity::class.java)
            requireContext().startActivity(intent)
        }

        if (firebaseUser != null) {
            val uid = firebaseUser!!.uid
            Toast.makeText(context, uid, Toast.LENGTH_SHORT)
                .show()
            loadProfile()
        } else {
            Toast.makeText(context, "Not signed in", Toast.LENGTH_SHORT)
                .show()
        }



        return binding.root
    }

    private fun loadProfile() {
        val user = Firebase.auth.currentUser
        binding.email.text = user.email
        binding.name.text = user.displayName
        Glide.with(requireActivity()).load(user.photoUrl).into(binding.profilePhoto)
    }

}