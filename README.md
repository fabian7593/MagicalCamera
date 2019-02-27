[![alt tag](https://raw.githubusercontent.com/fabian7593/MagicalCamera/master/Images/cameraHighQ.png)](https://github.com/fabian7593/MagicalCamera)

A Magic library to take photos and select pictures in Android. In a simple way and if you need it also save the pictures in device, and facial recognition, get the real uri path or the photo or obtain the private information of the picture.
<br>
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MagicalCamera-green.svg?style=true)](https://android-arsenal.com/details/1/3623)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a64cbf5369e14c3d98f8722c4ad3fad7)](https://www.codacy.com/app/fabian7593/MagicalCamera?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=fabian7593/MagicalCamera&amp;utm_campaign=Badge_Grade)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

<br>

<a href='https://play.google.com/store/apps/details?id=com.frosquivel.magicalcameraapp'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height='80'/></a>
<br>

# Contents

### Features
- - -
- [Features MagicalCamera](#features-magicalcamera)
- [Other Features](#other-features)

### Buy me a Coffee (Donate)
- [Donate](#donate)

### How to Start
- - -
- [Getting Started](#getting-started)
  - [Download Sources](#download-sources)
- [Setup](#setup)
  - [Add dependecies](#add-dependecies)
- [How To use](#how-to-use)
  - [Import library](#import-library)
  - [Extends Application Class](#extends-application-class)
  
### Photo Features and permissions
- - -
- [Permissions on real time](#permissions-on-real-time)
- [Photo](#photo)
  - [Declare variable to resize photo](#declare-variable-to-resize-photo)
  - [Instance Class MagicalCamera](#instance-class-magicalcamera)
  - [Activities Methods](#activities-methods)
  - [Fragments Methods](#fragments-methods)
  - [Override the event onActivityResult](#override-the-event-onactivityresult)
  - [Save Photo in Memory Devices](#save-photo-in-memory-devices)
  - [Get Uri path](#get-uri-path)
  - [Types of Formats for save photos](#types-of-formats-for-save-photos)
  
  - [Resize photo in real time](#resize-photo-in-real-time)
  - [Conversion Methods](#conversion-methods)
  - [Rotate picture](#rotate-picture)
  - [Facial Recognition](#facial-recognition)
  - [Private information Photo](#private-information-photo)
  
### Footer Docs
- - -
- [Footer Document](#footer-document)
  - [Internal documentation](#internal-documentation)
  - [Preview of Example](#preview-of-example)
  - [Application that use MagicalCamera](#application-that-use-magicalcamera)
  - [Suggestions](#suggestions)
- [Credits and contributors](#credits-and-contributors) 
  - [Contributors are welcome](#contributors-are-welcome)
- [Video Explanation](#video) 
- [Apache License](#license) 
  
<br><br>

## Features MagicalCamera.
* **Take picture** with camera device.
* **Select pictures in gallery** device (read in devices). 
* **Write the pictures** that you taken in device, in your own directory.
* Return the **path of your photo** in device. (This issue is solved for [@arthursz](https://github.com/arthursz))
* **RealTime Permissions** Magical camera offers a simple integration of realtime permissions. (This functionallity is created by [@cutiko](https://github.com/cutiko))

* Working in **android 6.0** (We have a class to request the user permission).
* Create yours **standards of name of pictures**, or use our standard, like "photoname_YYYYmmddHHmmss"
* Posibility of shown the **private info photography**, like latitude, longitude, ISO or others with Exif Class.
* Posibility of **rotate picture** when it's required.
* Select the **quality of the photo** with a percentage, when 1 is the worst and 100 is the better.
* Obtain the LandMark and return a bitmap with a **facial recognition** that you need.
* **Return the Bitmap**Photo if you need to save this in internal DB of your application.
* **Convert your bitmap** in array bytes or string64, if you need to send by Json or XML.
* **Type of photo formats:** PNG, JPEG and WEBP.

#### Other Features
* A library completely OpenSource.
* Use best practice in POO
* Minimun SDK 14+ API.
* Support library
* Compile with Gradle
* License Under Apache 2.0
* The easiest possible integration
* Integrate in less than 5 minutes
* Quick and simple API
* A good Internal Documentation

<br>
<br>

### Donate

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=L25MKCRPR7TWY)

<br>
<br><br>

## Getting Started

### Download Sources
use git (sourcetree or others)
Remember Download the example for understand better the process

```bash
git clone https://github.com/fabian7593/MagicalCamera.git
```

Download from [Here](https://github.com/fabian7593/MagicalCamera/zipball/master)

Another type download by Bintray from    [ ![Download](https://api.bintray.com/packages/fabian7593/maven/MagicalCamera/images/download.svg) ](https://bintray.com/fabian7593/maven/MagicalCamera/_latestVersion)

 <br>
 
## Setup
#### Add dependecies
If you need to take photo or select picture, this is your solution.
This library give a magical solution for take a picture,write and red in device, return your uri real path and obtain yhe private info of the photo and facial recognition, you only need to download this and integrate this in your project, maybe downloading it or import in your gradle, like this.

```bash
repositories {
    jcenter()
}

dependencies {
    compile 'com.frosquivel:magicalcamera:6.0.0'
}
```

If you have any problem with this dependence, because the library override any styles, colors or others, please change the last line for this code:

```bash
 compile('com.frosquivel:magicalcamera:6.0.0@aar') {
        transitive = false;
    }
```
 
<br>

## How To use
<br>

### Import library
You need to import the library
```bash
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.Functionallities.PermissionGranted;

//and maybe you need in some ocations
import com.frosquivel.magicalcamera.Objects.MagicalCameraObject;
```
<br>

### Extends Application Class
You need to extends MagicalCamera application

```bash
public class MyApplicationClass extends MagicalCameraApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }
    
    //IF YOU NEED MULTIDEX, SET HERE, NOT ON APPLICATION TAG ON MANIFEAST, LIKE THIS
    @Override
    protected void attachBaseContext(Context base) {
      super.attachBaseContext(base);
      MultiDex.install(this);
    }
}

```

On manifest, change the name, for the name of your Application class, like this:

```bash
  <application
        android:name=".MyApplicationClass">
```


### Permissions on real time
With the MagicalPermissions class you can ask for permissions in a Activity or in an Fragment. This class will take care of validating the device API level, what permissions the user haven't granted yet, ask for thoose permissions, deliver the result and together with MagicalCamera will take the photo or select it from the gallery.

Requesting permissions on real time to the user is a 2 part process. First permissions most be requested, then the result is delivered. So you need a field variable to later call it again on the permissions result:

```
private MagicalPermissions magicalPermissions;
```

MagicalPermissions constructor accept two params, the first is the Activity or Fragment and the second is a String array of the permissions you **need** `String[]`. MagicalPermissions use the Activity or Fragment to later deliver the result of the permission, and the array to ask for thoose permissions.

```
magicalPermissions = new MagicalPermissions(this, permissions);
```

Inside MagicalPermissions it will be solved if the API level of the device requiere to ask permissions or not. If permissions must be asked to the user then MagicalPermissions will validate which permissions are already granted and only asked for the needed. **This is why is very important you only ask for the permissions you need, taking the photo or selecting it will only happen if every permission you have asked is granted**. 

 - By example, if you only need to take the photo then:

```
String[] permissions = new String[] {
                Manifest.permission.CAMERA
        };
magicalPermissions = new MagicalPermissions(this, permissions);
```

- MagicalCamera take care of saving the photo commonly you will need:

```
String[] permissions = new String[] {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
magicalPermissions = new MagicalPermissions(this, permissions);
```

 - Or maybe you want to use more potential of MagicalCamera and also ask for location related info, then you need:

```
String[] permissions = new String[] {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
magicalPermissions = new MagicalPermissions(this, permissions);
```

You can also ask for other permissions in other places of your app, even unrelated to MagicalCamera using MagicalPermissions separatedly. So if getting location information is not requiered for the photo, but is a plus, you should consider separating thoose permissions from the absolutely needed to your feature. MagicalPermissions use a `Runnable` to do whatever you want after checking the permissions. When is used along with MagicalCamera you don't have to be aware of that, is automatic, but in this case we are considering asking for other permissions:

```
String[] location = new String[] {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
locationPermissions = new MagicalPermissions(this, location);


Runnable runnable = new Runnable() {
    @Override
    public void run() {
        //TODO location permissions are granted code here your feature
        Toast.makeText(context, "Thanks for granting location permissions", Toast.LENGTH_LONG).show();
    }
};
locationPermissions.askPermissions(runnable);
```

Then the second part of the process come into play: receiving the permissions result. You have to **always override** the `onRequestPermissionsResult` method in the Activity or Fragment. Inside of it, call your instance of MagicalPermissions and give it the result to the `permissionResult()` method (this is why must be a field). The `permissionResult()` method is a `Map<String, Boolean>` in case you need to know what happened and do something about it:

```
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    Map<String, Boolean> map = magicalPermissions.permissionResult(requestCode, permissions, grantResults);
    for (String permission : map.keySet()) {
        Log.d("PERMISSIONS", permission + " was: " + map.get(permission));
    }
    //Following the example you could also
    //locationPermissions(requestCode, permissions, grantResults);
}
```

<br>

# Photo

### Declare variable to resize photo 
**( with pixels percentage )**
You need to declare and constant or a simple int variable for the quality of the photo, while greater be, greater be the quality, and otherwise, worst be the quality, like this
```bash
//The pixel percentage is declare like an percentage of 100, if your value is 50, the photo will have the middle quality of your camera. 
// this value could be only 1 to 100.
private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 80;
```

<br>

### Instance Class MagicalCamera
###### *YOU NEED TO INSTANCE THIS, AFTER THAT PERMISSION GRANTED INSTANCE.*
You need to instance the MagicalCamera Class, like this:
The fisrt param is the current Activity, and the second the resize percentage photo, and the third param is the Permission Granted
```bash
 MagicalCamera magicalCamera = new MagicalCamera(this,RESIZE_PHOTO_PIXELS_PERCENTAGE, magicalPermissions);
 
```

<br>

### Activities Methods
You need to call the methods for take or select pictures in activities that this form:

```bash
//take photo
magicalCamera.takePhoto();

//select picture
magicalCamera.selectedPicture("my_header_name");
```

<br>

### Fragments Methods
You need to call these methods for take or select pictures in fragments:

```bash
//take photo
magicalCamera.takeFragmentPhoto(FragmentSample.this);
 
 //select picture
magicalCamera.selectedFragmentPicture(FragmentSample.this, "My Header Example");
```
<br>
As you can see MagicalCamera is working together MagicalPermissions so you don't need to pass a `Runnable` the camera or photo selection will be triggered once permissions are solved.
<br>

### Override the event onActivityResult
**Remember**, you **need to override** the method onActivityResult in your activity or fragment like this
```bash
 @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //CALL THIS METHOD EVER
        magicalCamera.resultPhoto(requestCode, resultCode, data);
        
        //this is for rotate picture in this method
        //magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_180);
        
        //with this form you obtain the bitmap (in this example set this bitmap in image view)
        imageView.setImageBitmap(magicalCamera.getPhoto());
        
        //if you need save your bitmap in device use this method and return the path if you need this
        //You need to send, the bitmap picture, the photo name, the directory name, the picture type, and autoincrement photo name if           //you need this send true, else you have the posibility or realize your standard name for your pictures.
        String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(),"myPhotoName","myDirectoryName", MagicalCamera.JPEG, true);

       if(path != null){
           Toast.makeText(MainActivity.this, "The photo is save in device, please check this path: " + path, Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(MainActivity.this, "Sorry your photo dont write in devide, please contact with fabian7593@gmail and say this error", Toast.LENGTH_SHORT).show();
       }
    }
```

<br>

### Get Uri path
You only could use
```bash
magicalCamera.getRealPath()
```

### Save Photo in Memory Devices
This method save your bitmap in internal memory device or if the internal memory is full this library save in sdcard (if you have anything)
This method have a lot of params that you can need to use the library:
* **Bitmap:** This is the bitmap that you need to save in memory device.
* **PhotoName:** The name of the photo
* **DirectoryName:** The name of directory that you need to save the image
* **Format:** the format of the photo, maybe png, jpeg or webp. Depends of that you need.
* **AutoIncrementNameByDate:** This variable save the photo with the photo name and the current date and hour. (Only if is true).

For example: myTestMagicalCameraPhoto_20160520131344 -> This is the year 2016, month 5, day 20, hour 13, minute 13 and second 44.

The method:
```bash
 public String savePhotoInMemoryDevice(Bitmap bitmap, String photoName, String directoryName,
 Bitmap.CompressFormat format, boolean autoIncrementNameByDate)
```

Example:
```bash
 String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "myTestPhotoName", MagicalCamera.JPEG, true);
```
<br>

### Types of Formats for save photos
You have any type of formats for save the pictures and the bitmaps.
You can use, the static variables of the library MagicalCamera.
```bash
 Bitmap.CompressFormat jpeg = MagicalCamera.JPEG;
 Bitmap.CompressFormat png = MagicalCamera.PNG;
 Bitmap.CompressFormat webp = MagicalCamera.WEBP;
```

<br>

### Resize photo in real time
You can resize the photo in any moment with this:
```bash
   magicalCamera.setResizePhoto(newResizeInteger);
```

<br>

### Conversion Methods
The library have any methods to convert the bitmap in other formats that you need.
All of this methods are public statics, I mean that you dont have to instance the library for usage this.
You need to call the class * ConvertSimpleImage * And the respective params.
* **bitmapToBytes:** Convert the bitmap to array bytes, only need the bitmap param and the compress format, return array bytes.
* **bytesToBitmap:** Convert the array bytes to bitmap, only need the array bytes in param, return bitmap.
* **bytesToStringBase64:** Convert the array bytes to String base 64, only need the array bytes format in param, return String.
* **stringBase64ToBytes:** Convert string to array bytes, only need the String in param, return array bytes.

Example:
```bash
  //convert the bitmap to bytes
  byte[] bytesArray =  ConvertSimpleImage.bitmapToBytes(magicalCamera.getPhoto(), MagicalCamera.PNG);
  
   //convert the bytes to string 64, with this form is easly to send by web service or store data in DB
   String imageBase64 = ConvertSimpleImage.bytesToStringBase64(bytesArray);

   //if you need to revert the process
   byte[] anotherArrayBytes = ConvertSimpleImage.stringBase64ToBytes(imageBase64);

  //again deserialize the image
  Bitmap myImageAgain = ConvertSimpleImage.bytesToBitmap(anotherArrayBytes);
```

<br>

### Rotate picture
You have the posibility of rotate picture because some devices have the camera in landscape, and the picture is shown upside down.
If you need to rotate image use in event onActivityResult the method with the last param:
```bash
  magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_180);
``` 
or if you rotate manually in another part of code use method:
```bash
//rotate any image
Bitmap myImage = magicalCamera.rotatePicture(magicalCamera.getPhoto(),  MagicalCamera.ORIENTATION_ROTATE_90);

//rotate the getPhoto in magicalCamera Object
Bitmap myImage = magicalCamera.rotatePicture(MagicalCamera.ORIENTATION_ROTATE_NORMAL);
``` 

You have this posibillities of rotate image:
```bash
MagicalCamera.ORIENTATION_ROTATE_NORMAL
MagicalCamera.ORIENTATION_ROTATE_90
MagicalCamera.ORIENTATION_ROTATE_180
MagicalCamera.ORIENTATION_ROTATE_270
``` 
<br>

### Facial Recognition:

This is a method to return your bitmap (magicalCamera.getPhoto()) like another bitmap with a square draws arround the face of the photo, with the posibillity of modify the color and the stroke of the square. And this is not all, you have the posibility of call the List<Landmark> of the photo with facial recognitions, for save data of all faces, for example the distance between eyes, the nose position and mounth position, all of this is important information for facials recognitions.

You need to write for example:
```bash
if(magicalCamera != null){
    if(magicalCamera.getPhoto() != null){
         //this comment line is the strok 5 and color red for default
         //imageView.setImageBitmap(magicalCamera.faceDetector());
         //you can the posibility of send the square color and the respective stroke
         imageView.setImageBitmap(magicalCamera.faceDetector(50, Color.GREEN));

         List<Landmark> listMark = magicalCamera.getListLandMarkPhoto();
     }else{
         Toast.makeText(MainActivity.this,
                 "Your image is null, please select or take one",
                 Toast.LENGTH_SHORT).show();
     }
 }else{
     Toast.makeText(MainActivity.this,
             "Please initialized magical camera, maybe in static context for use in all activity",
             Toast.LENGTH_SHORT).show();
 }
```

The photo and bitmap converted is like to:
<br>

![alt tag](https://raw.githubusercontent.com/fabian7593/MagicalCamera/master/Images/faceDetection2.png)


### Private information Photo:
This method show you the private information photo if the photo is saved in device or not... 
For view all information the device need to activate GPS locations (and maybe internet), else not show all information :(.

You need to write this code for example:
```bash
 //verify if the bitmap of image have data
 if(magicalCamera.getPhoto()!=null) {
   //verify if this photo is save in device, and if has private information to show and return true if have information, or false is not
   if(magicalCamera.initImageInformation()) {

         StringBuilder builderInformation = new StringBuilder();

         if (notNullNotFill(magicalCamera.getPrivateInformation().getLatitude() + ""))
             builderInformation.append("Latitude: " + magicalCamera.getPrivateInformation().getLatitude() + "\n");

         if (notNullNotFill(magicalCamera.getPrivateInformation().getLatitudeReference()))
             builderInformation.append("Latitude Reference: " + magicalCamera.getPrivateInformation().getLatitudeReference() + "\n");

         if (notNullNotFill(magicalCamera.getPrivateInformation().getLongitude() + ""))
             builderInformation.append("Longitude: " + magicalCamera.getPrivateInformation().getLongitude() + "\n");

         if (notNullNotFill(magicalCamera.getPrivateInformation().getLongitudeReference()))
             builderInformation.append("Longitude Reference: " + magicalCamera.getPrivateInformation().getLongitudeReference() + "\n");

         if (notNullNotFill(magicalCamera.getPrivateInformation().getDateTimeTakePhoto()))
             builderInformation.append("Date time to photo: " + magicalCamera.getPrivateInformation().getDateTimeTakePhoto() + "\n");

         if (notNullNotFill(magicalCamera.getPrivateInformation().getDateStamp()))
             builderInformation.append("Date stamp to photo: " + magicalCamera.getPrivateInformation().getDateStamp() + "\n");

         if (notNullNotFill(magicalCamera.getPrivateInformation().getIso()))
             builderInformation.append("ISO: " + magicalCamera.getPrivateInformation().getIso() + "\n");

         if (notNullNotFill(magicalCamera.getPrivateInformation().getOrientation()))
             builderInformation.append("Orientation photo: " + magicalCamera.getPrivateInformation().getOrientation() + "\n");

         if (notNullNotFill(magicalCamera.getPrivateInformation().getImageLength()))
             builderInformation.append("Image lenght: " + magicalCamera.getPrivateInformation().getImageLength() + "\n");

         if (notNullNotFill(magicalCamera.getPrivateInformation().getImageWidth()))
             builderInformation.append("Image Width: " + magicalCamera.getPrivateInformation().getImageWidth() + "\n");

         if (notNullNotFill(magicalCamera.getPrivateInformation().getModelDevice()))
             builderInformation.append("Model Device: " + magicalCamera.getPrivateInformation().getModelDevice() + "\n");

         if (notNullNotFill(magicalCamera.getPrivateInformation().getMakeCompany()))
             builderInformation.append("Make company: " + magicalCamera.getPrivateInformation().getMakeCompany() + "\n");

         new MaterialDialog.Builder(MainActivity.this)
                 .title("See photo information")
                 .content(builderInformation.toString())
                 .positiveText("ok")
                 .show();
   }else{
   Toast.makeText(MainActivity.this,
           "This photo donte have ifnormation, remember, for obtain the info you need to save the picture in device before",
           Toast.LENGTH_SHORT).show();
   }
 }else{
   Toast.makeText(MainActivity.this,
           "You dont have data to show because the photo is null (your photo isn't in memory device)",
           Toast.LENGTH_SHORT).show();
 }
 
 
 
 and the method that I use in the example is for validate not null or empty
    private boolean notNullNotFill(String validate){
        if(validate != null){
            if(!validate.trim().equals("")){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
```

See the example of this infomartion return:
<br>

![alt tag](https://raw.githubusercontent.com/fabian7593/MagicalCamera/master/Images/information2.png)

<br><br><br>

# Footer Document

## Internal documentation
All the code has a internal documentation for more explanation of this example.

<br><br>

## Preview of Example
<br>

![alt tag](https://raw.githubusercontent.com/fabian7593/MagicalCamera/master/Images/magicalcamera.gif)

<br><br>

## Application that use MagicalCamera
UTNCources<br>
ExampleMagicalCamera

Feel free to contact me to add yours apps to this list.

<br><br>

## Suggestions
MagicalCamera was created to make Android Devoloper's life easy. If you have any feedback please let us know in the issues by creating an issue with this format:
 
 - Write what your feedback is about and add the next "tag" including the square brackets [FEEDBACK]

Suggestions about how to improve the library or new features are welcome. Thanks for choosing us.
<br><br>

## Credits and Contributors

**Author**
### [Fabián Rosales - Frosquivel Developer](https://github.com/fabian7593) : 
A magical camera author, I do the take camera, select photo, rotate picture, convert bitmap, facial recognition, save picture, get information and others...

[![alt tag](https://raw.githubusercontent.com/fabian7593/CountryAPI/master/Files/imgsReadme/github-logo.png)](https://github.com/fabian7593)
[![alt tag](https://raw.githubusercontent.com/fabian7593/CountryAPI/master/Files/imgsReadme/facebook.png)](https://www.facebook.com/fabian.rosales.509)
[![alt tag](https://raw.githubusercontent.com/fabian7593/CountryAPI/master/Files/imgsReadme/linkedin.png)](https://www.linkedin.com/in/fabian-rosales-esquivel-698893106/)
[![alt tag](https://raw.githubusercontent.com/fabian7593/CountryAPI/master/Files/imgsReadme/youtube.png)](https://www.youtube.com/channel/UCJnvvHb_vwMwbnZWplkHIfw)

<br>

**Contributors**
### [Erick Navarro](https://github.com/cutiko)
**MagicalCamera Contributor (Cutiko)**
Erick Add a best usage of google play library, and he develop the better usage of permissions, and an excellent code refactor for permission class and other components.

### [Arthur Zettler](https://github.com/arthursz)
**MagicalCamera Contributor (arthursz)**
Arthur create the return path of the image saved like a String.

<br><br>

## Contributors are welcome
The goal for MagicalCamera is to allow Android Developers care about what is important, feautures not getting worry about something that should be trivial such as taking a picture. We look forward to make this a great library to make image capture process simple and painless. There are amny features and other issues waiting. If you would like to contribute please reach to us, or maybe be bold! Getting a surprise pull request is very gratifying.

<br><br>

## Video
## You can see the video explication here (in spanish) This video is for MagicalCamera version 1.0
https://www.youtube.com/watch?v=U-JxaFZDSn4

<br><br><br>

# License
Copyright 2016 Fabian Rosales

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
<br><br>

