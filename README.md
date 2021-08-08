## The-Real-Jigsaw

This is an awesome and a next level Jigsaw puzzle game that you have never seen. The game contains three modes: **World Mode**, **Custom Mode** and **Special Mode**. The **World Mode** allows the user to create Jigsaw puzzle of the real world. The users may either use the rear camera to create a puzzle of the real world or the front camera of their android phone to create a Jigsaw puzzle of their face in various modes like **3x3**, **4x4** or **5x5**. 
The **Custom Mode** allows the user to play the game just like a conventional Jigsaw game.
The **Special Mode** allows the user to apply various filters like Green, Blue, Red etc. to the images captured by their camera and then create a Jigsaw puzzle out of it.
The users may see the list of all puzzles played by them and the corresponding scores in the **My Scores** section. The **Leaderboard** contains the high scores of all the users.

### Demo

<div>
    <p>SignIn and Register activities</p>
<img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-073613_The%20Real%20Jigsaw.jpg" alt="image" width="200"/>
<img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-073620_The%20Real%20Jigsaw.jpg" alt="image" width="200">
</div>

<div>
    <div>
        <p>The Home Activity</p>
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-073738_The%20Real%20Jigsaw.jpg" alt="image" width="170"/>
    </div>
    <div>
        <p>Special Mode with various filters</p>
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-081307_The%20Real%20Jigsaw.jpg" alt="Image" width="170"/>
        &nbsp;&nbsp;
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-081321_The%20Real%20Jigsaw.jpg" alt="Image" width="170"/>
        &nbsp;&nbsp;
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-081340_The%20Real%20Jigsaw%20(1).jpg" alt="Image" width="170"/>
        &nbsp;&nbsp;
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-075837_The%20Real%20Jigsaw.jpg" alt="Image" width="170"/>
        &nbsp;&nbsp;
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-080530_The%20Real%20Jigsaw.jpg" alt="Image" width="170"/>
    </div>
    <div>
        <p>Custom Mode</p>
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-075645_The%20Real%20Jigsaw.jpg" alt="Image" width="170"/>
        &nbsp;&nbsp;
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-111313_The%20Real%20Jigsaw.jpg" alt="Image" width="170"/>
        &nbsp;&nbsp;
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-075635_The%20Real%20Jigsaw.jpg" alt="Image" width="170"/>
    </div>
    <div>
        <p>World Mode in various grid sizes</p>
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-112504_Camera.jpg" alt="Images" width="170"/>
        &nbsp;&nbsp;
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-112317_The%20Real%20Jigsaw.jpg" alt="Images" width="170"/>
        &nbsp;&nbsp;
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-112230_The%20Real%20Jigsaw.jpg" alt="Images" width="170"/>
        &nbsp;&nbsp;
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-112344_The%20Real%20Jigsaw.jpg" alt="Images" width="170"/>
    </div>
    <div>
        <p>Leaderboard, My Scores and Winning Activity</p>
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-102128_The%20Real%20Jigsaw.jpg" alt="Images" width="170"/>
        &nbsp;&nbsp;
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-100122_The%20Real%20Jigsaw.jpg" alt="Images" width="170"/>
        &nbsp;&nbsp;
        <img src="https://github.com/r4ksh1t0011/Real-World-Jigsaw/blob/images/Screenshot_20210808-075503_The%20Real%20Jigsaw.jpg" alt="Images" width="170"/>
    </div>
</div>

### About the Server
Node.js has been used to build the web server for saving data like their username, password, email for the app. Password has been stored safely after applying a strong hashing algorithm. MongoDB is used to perform the database operations.


### Setup and Installation
Download the [APK](https://drive.google.com/file/d/19Zn3Nj5-IAB-k93MfFtIoGYtDshRyNxb/view?usp=sharing) and install it

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

