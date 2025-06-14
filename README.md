# 🎧 SmartAudioVisualizer

**SmartAudioVisualizer** is a lightweight and fully customizable Android library that displays beautiful **real-time audio visualizer bars** — just like native audio recording apps.

This visualizer is perfect for:
- Audio/voice recording apps 🎤
- Music players 🎵
- Podcast/meeting tools 🎙️
- Sound-responsive UIs 🌈

It supports both:
- 🔹 Simulated demo wave (for preview or offline use)
- 🔹 Live microphone input (real audio visualization)

---

## 📦 Installation

### 1. Add JitPack to your `settings.gradle` (Project-level)

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
2. Add the dependency to your build.gradle (App-level)
Replace 1.0 with the latest version if updated:

groovy
Copy
Edit
dependencies {
    implementation 'com.github.ankug6283:SmartAudioVisualizer:1.0'
}
🛠️ XML Usage
Add the visualizer to your layout:

xml
Copy
Edit
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
💡 XML Attributes
Attribute	Description
barColor	Color of each bar
barCount	Total number of bars
barWidth	Width of individual bars
barSpacing	Gap between bars
barSpeed	Millisecond interval for animation

🧪 Simulated Demo Wave (No Mic Required)
For showing visualizer without real audio input — great for preview or testing:

java
Copy
Edit
SmartAudioVisualizer visualizer = findViewById(R.id.barVisualizer);
visualizer.simulateDemoWave();
🎤 Real-Time Audio Visualizer (Mic Input)
1. Add permission in AndroidManifest.xml:
xml
Copy
Edit
<uses-permission android:name="android.permission.RECORD_AUDIO" />
2. Full Java Code:
java
Copy
Edit
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
🧩 Customization Ideas
Show/hide visualizer based on recording state

Add fade-in/out animations

Match theme dynamically with user audio level

Use in background audio apps with waveform exports (future update)

🧑‍💻 Developed By
@ankug6283
Open to suggestions, contributions, and improvements!

📄 License
This project is released under the MIT License.

💬 Having Issues?
Check your mic permissions.

Use simulateDemoWave() for quick testing without mic access.

Make sure JitPack build is successful at:
https://jitpack.io/#ankug6283/SmartAudioVisualizer