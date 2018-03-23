package com.vncode247.dexter_runtimepermission

import android.Manifest
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvRequestPermission = findViewById<TextView>(R.id.tvRequestPermission)
        tvRequestPermission.setOnClickListener {
            validatePermission()
        }
    }

    private fun validatePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        Toast.makeText(this@MainActivity, "Permission Granted", Toast.LENGTH_LONG).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                        AlertDialog.Builder(this@MainActivity)
                                .setTitle(R.string.storage_permission_rationale_title)
                                .setMessage(R.string.storage_permission_rationale_message)
                                .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener {
                                    dialogInterface, i ->
                                    dialogInterface.dismiss()
                                    token?.cancelPermissionRequest()
                                })
                                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener {
                                    dialogInterface, i ->
                                    dialogInterface.dismiss()
                                    token?.continuePermissionRequest()
                                })
                                .show()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(this@MainActivity, R.string.storage_permission_denied_message, Toast.LENGTH_LONG).show()
                    }
                }
                ).check()
    }

}
