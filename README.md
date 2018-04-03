# android-kotlin-dexter-runtime-request-permission
Dexter with Kotlin in Android

[![Dexter with Kotlin in Android](https://i.imgur.com/yK1J9O8.png)](https://youtu.be/zXWqMvSdyJ4)

```kotlin
// app/build.gradle
implementation 'com.karumi:dexter:4.2.0'
```
```kotlin
// AndroidManifest.xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```
```kotlin
// MainActivity.kt
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
```
