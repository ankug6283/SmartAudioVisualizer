# 🎧 SmartAudioVisualizer

**SmartAudioVisualizer** is a lightweight and customizable Android library that displays real-time audio visualizer bars like those seen in voice recorders and music apps.

---

## 📦 Installation

### 1. Add JitPack to `settings.gradle` (Project level)

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### 2. Add the dependency to `build.gradle` (App level)

```groovy
dependencies {
    implementation 'com.github.ankug6283:SmartAudioVisualizer:1.0'
}
```

---

## 🛠️ XML Usage

```xml
<Abhi6283.smartaudiovisualizer.SmartAudioVisualizer
    android:id="@+id/barVisualizer"
    android:layout_width="match_parent"
    android:layout_height="60dp"
    android:layout_margin="16dp"
    app:barColor="@color/black"
    app:barCount="100"
    app:barWidth="2dp"
    app:barSpacing="2dp"
    app:barSpeed="100" />
```

---

## 🧪 Simulated Demo Wave

```java
 SmartAudioVisualizer visualizer = findViewById(R.id.barVisualizer);
// visualizer.simulateDemoWave();
```

---

## 🎤 Real-Time Audio Visualizer

### Add permission in `AndroidManifest.xml`

```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```

### Java Code

```java
private AudioRecord audioRecord;
private boolean isRecording = false;
private Thread recordingThread;

private void startRealTimeVisualizer(SmartAudioVisualizer visualizer) {
    int sampleRate = 44100;
    int bufferSize = AudioRecord.getMinBufferSize(sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT);

    audioRecord = new AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
    );

    isRecording = true;
    audioRecord.startRecording();

    recordingThread = new Thread(() -> {
        byte[] buffer = new byte[bufferSize];
        while (isRecording) {
            int read = audioRecord.read(buffer, 0, buffer.length);
            if (read > 0) {
                visualizer.updateVisualizer(buffer);
            }
        }
    });
    recordingThread.start();
}

private void stopVisualizer() {
    isRecording = false;
    if (audioRecord != null) {
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;
    }
    if (recordingThread != null) {
        recordingThread.interrupt();
        recordingThread = null;
    }
}
```

---

## 💡 Customization

You can customize bar color, count, spacing, speed, and more using XML attributes.

---

## 🧑‍💻 Author

**[@ankug6283](https://github.com/ankug6283)**  
Proudly open-source, MIT licensed.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).