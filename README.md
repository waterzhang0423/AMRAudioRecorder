# AMRAudioRecorder
Android does not support pause and resume when recording amr audio, so we should do a little trick to support pause and resume funciton.
## Screenshot
![Screenshot](Screenshot/demo.jpg)
## Features
* You can pause recording and resume it

## Usage
In Android Studio, just import module `amraudiorecorder`. In other IDE, you should copy `AMRAudioRecorder.java` into your project.

~~~java
// Note: this is not the audio file name, it's a directory.
// AMRAudioRecorder will store audio files into this directory.
// And this should be exist,
// AMRAudioRecorder will not make dir if the dir does not exist.
String recordingDirectory = "A directory absolute path";
AMRAudioRecorder  mRecorder = new AMRAudioRecorder(recordingDirectory);
mRecorder.start();
~~~
## Pause recording
~~~java
mRecorder.pause();
~~~
## Resume recording
~~~java
mRecorder.resume();
~~~
## Stop recording
~~~java
mRecorder.stop();
~~~
## Get recording file path
~~~java
mRecorder.getAudioFilePath();
~~~