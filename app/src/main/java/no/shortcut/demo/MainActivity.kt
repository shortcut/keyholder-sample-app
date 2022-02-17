package no.shortcut.demo

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import no.shortcut.demo.Utils.bytesToHex

import no.shortcut.demo.databinding.ActivityMainBinding
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewNative.text = "NDK: ${getSigningFingerprintNDK(this)}"
        binding.textViewKotlin.text = "Kotlin: ${getSigningFingerprintKotlin()}"

        binding.textViewSecret.text = "Secret key: ${getSecretKeyNDK(this)}"

    }

    //Native functions implemented inside the keyholder lib
    external fun getSigningFingerprintNDK(context: Context): String
    external fun getSecretKeyNDK(context: Context): String

    companion object {
        init {
            System.loadLibrary("keyholder-lib")
        }
    }

    private fun getSigningFingerprintKotlin(): String? {

        val arrayOfSignatures = packageManager.getPackageInfo(this.packageName, PackageManager.GET_SIGNATURES).signatures

        return if(arrayOfSignatures.isNotEmpty()) {
            val messageDigest = MessageDigest.getInstance("SHA256")
            messageDigest.update(arrayOfSignatures.get(0).toByteArray())
            val digest = messageDigest.digest()
            digest.bytesToHex()
        } else {
            null
        }
    }
}

