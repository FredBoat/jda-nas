# What the fork is this?

The [FredBoat](https://github.com/Frederikam/FredBoat) team uses this fork of [jda-nas](https://github.com/sedmelluq/jda-nas) to try out adjustments before committing them back upstream.


# JDA-NAS - JDA Native Audio System

JDA-NAS is an alternative to JDA's built-in audio packet sending system. It keeps a buffer of audio packets in native code and also sends them from there. This way it is unaffected by GC pauses shorter than the duration of the buffer (400ms by default) and gets rid of stuttering caused by those.

#### Maven package

* Repository: jitpack.io
* Artifact: **com.github.FredBoat:jda-nas:1.0.5.1-JDA** or **com.github.FredBoat:jda-nas:1.0.5.1-JDA-Audio** depending whether you are using the full [JDA](https://github.com/DV8FromTheWorld/JDA) lib or the audio-only spin-off [JDA-Audio](https://github.com/DV8FromTheWorld/JDA-Audio)

Using in Gradle:
```groovy
repositories {
  maven {
    url 'https://jitpack.io'
  }
}

dependencies {
  compile 'com.github.FredBoat:jda-nas:1.0.5.1-JDA'
  //or
  compile 'com.github.FredBoat:jda-nas:1.0.5.1-JDA-Audio'
}
```

Using in Maven:
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.github.FredBoat</groupId>
    <artifactId>jda-nas</artifactId>
    <version>1.0.5.1-JDA</version>
    <!-- or -->
    <version>1.0.5.1-JDA-Audio</version>
  </dependency>
</dependencies>
```


## Usage

Using it is as simple as just calling calling this on a JDABuilder:
```java
.setAudioSendFactory(new NativeAudioSendFactory())
```

For example:
```java
new JDABuilder(AccountType.BOT)
    .setToken(System.getProperty("botToken"))
    .setAudioSendFactory(new NativeAudioSendFactory())
    .buildBlocking()
```

## Supported platforms

As it includes a native library, it is only supported on a specific set of platforms currently:

* Windows (x86 and x64)
* Linux (x86 and x64, glibc >= 2.15)
