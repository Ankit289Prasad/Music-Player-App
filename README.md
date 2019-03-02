# *Music-Player-App*

## Easy way of making music player

### *Coding Platform*
Java

### *Permissions*
 
Takes permission for reading music files from the phone storage using dexter library.

Dexter is an Android library that simplifies the process of requesting permissions at runtime.

Android Marshmallow includes a new functionality to let users grant or deny permissions when running an app instead of granting them all when installing it. This approach gives the user more control over applications but requires developers to add lots of code to support it.

The official API is heavily coupled with the ``Activity`` class.
Dexter frees your permission code from your activities and lets you write that logic anywhere you want.


Usage
-----

### Dependency

Include the library in your ``build.gradle``

```groovy
dependencies{
    implementation 'com.karumi:dexter:5.0.0'
}
```


To start using the library you just need to call `Dexter` with a valid `Activity`:

```java
public MyActivity extends Activity {
	@Override public void onCreate() {
		super.onCreate();
		Dexter.withActivity(activity)
			.withPermission(permission)
			.withListener(listener)
			.check();
	}
}
```

### Single permission
For each permission, register a ``PermissionListener`` implementation to receive the state of the request:

```java
Dexter.withActivity(this)
	.withPermission(Manifest.permission.CAMERA)
	.withListener(new PermissionListener() {
		@Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
		@Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}
		@Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
	}).check();
```

### *UI (User Interface)*
Simple and easy created UI,best for beginners.

# Hope you like it. 😊
