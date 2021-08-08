## The-Real-Jigsaw

This is an awesome and a next level Jigsaw puzzle game that you have never seen. The game contains three modes: **World Mode**, **Custom Mode** and **Special Mode**. The **World Mode** allows the user to create Jigsaw puzzle of the real world. The users may either use the rear camera to create a puzzle of the real world or the front camera of their android phone to create a Jigsaw puzzle of their face in various modes like **3x3**, **4x4** or **5x5**. 
The **Custom Mode** allows the user to play the game just like a conventional Jigsaw game.
The **Special Mode** allows the user to apply various filters like Green, Blue, Red etc. to the images captured by their camera and then create a Jigsaw puzzle out of it.
The users may see the list of all puzzles played by them and the corresponding scores in the **My Scores** section. The **Leaderboard** contains the high scores of all the users.

### Demo


### About the Server
Node.js has been used to build the web server for saving data like their username, password, email for the app. Password has been stored safely after applying a strong hashing algorithm. MongoDB is used to perform the database operations.


### Setup and Installation
pass

If you want to install the app through Android Studio, follow the following instructions:
Clone the repository:
```
git clone https://github.com/r4ksh1t0011/Real-World-Jigsaw
```
##### Extra libraries used: 
[android-gif-drawable](https://github.com/koral--/android-gif-drawable) for rendering animated GIFs for enhanced visual effects.
To use it, insert the following dependency to `build.gradle` file of your project.
```
dependencies {
    implementation 'pl.droidsonroids.gif:android-gif-drawable:1.2.22'
}
```
Note that Maven central repository should be defined eg. in top-level `build.gradle` like this:
```
buildscript {
    repositories {
        mavenCentral()
    }
}
allprojects {
    repositories {
        mavenCentral()
    }
}
```

[Photo Filters SDK](https://github.com/Zomato/AndroidPhotoFilters) for applying Instagram like filters to the user's image in Special Mode.
Adding dependency:
```
dependencies {
    compile 'com.github.zomato:androidphotofilters:1.0.2'
    ...
```
[Volley](https://github.com/google/volley) for posting data such as username,email, scores etc. and rendering the **Leaderboard** and **My Scores**
```
dependencies {
    implementation 'com.android.volley:volley:1.2.0'
    ...
```    

