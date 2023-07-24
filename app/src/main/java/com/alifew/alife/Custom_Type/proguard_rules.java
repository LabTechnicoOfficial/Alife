package com.alifew.alife.Custom_Type;

public class proguard_rules {
    /*-ignorewarnings
-keep class * {
        public private *;
    }
-keep class androidx.lifecycle.* { *; }
-keep class * extends androidx.lifecycle.ViewModel {
    <init>();
    }
-keep class * extends androidx.lifecycle.AndroidViewModel {
    <init>(android.app.Application);
    }
-keep class com.firebase.* { *; }
-keepnames class com.fasterxml.jackson.* { *; }
-keepnames class javax.servlet.* { *; }
-keepnames class org.ietf.jgss.* { *; }
-dontwarn org.w3c.dom.**
            -dontwarn org.joda.time.**
            -dontwarn org.shaded.apache.**
            -dontwarn org.ietf.jgss.**
            -dontwarn com.firebase.**
            -dontnote com.firebase.client.core.GaePlatform
-keep class com.google.android.gms.*{ *; }
-dontwarn com.google.android.gms.**

            -dontwarn org.xmlpull.v1.**
            -dontnote org.xmlpull.v1.**
            -keep class org.xmlpull.* { *; }

#retRofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
            -dontnote retrofit2.Platform.IOS.MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
            -dontwarn retrofit2.Platform.Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
-keepclasseswithmembers class * {
        @retrofit2.http.* <methods>;
    }
# Orm

-keepclassmembers class * {

    }
-keepclassmembers class * {
        native <methods>;
    }
-dontwarn okio.**
            -dontwarn com.squareup.okhttp.**
            -dontwarn okhttp3.**
            -dontwarn javax.annotation.**
            -dontwarn com.android.volley.toolbox.**

            # com.github.siyamed:android-shape-imageview
-dontwarn android.support.v7.**
            -keepattributes *Annotation,Signature
-dontwarn com.github.siyamed.**

            -dontwarn org.xmlpull.v1.**
            -dontwarn uk.co.senab.photoview.**

            #Image Cropper
-keep class androidx.appcompat.widget.* { *; }



# Keep source file names, line numbers, and Parse class/method names for easier debugging
 -keepattributes SourceFile,LineNumberTable
 -keepnames class com.parse.* { *; }


 # Required for Parse
 -keepattributes *Annotation*
            -keepattributes Signature
 -dontwarn com.squareup.**
            -dontwarn okio.**


            -keepattributes SourceFile,LineNumberTable
-keep class com.parse.*{ *; }
-dontwarn com.parse.**
            -dontwarn com.squareup.picasso.**
            -keepclasseswithmembernames class * {
        native <methods>;
    }*/
}
