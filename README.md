1.Add the dependency to your build.gradle (App-level)

In build.gradle module file

dependencies {

implementation 'com.github.ankug6283:SmartAudioVisualizer:1.0'

}

in settings.gradle file

dependencyResolutionManagement {
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
repositories {
google()
mavenCentral()
maven { url 'https://jitpack.io' }
}
}

🛠️ XML Usage
Add the visualizer to your layout:

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
| Attribute    | Description                        |
| ------------ | ---------------------------------- |
| `barColor`   | Color of each bar                  |
| `barCount`   | Total number of bars               |
| `barWidth`   | Width of individual bars           |
| `barSpacing` | Gap between bars                   |
| `barSpeed`   | Millisecond interval for animation |



🧪 Simulated Demo Wave (No Mic Required)
For showing visualizer without real audio input — great for preview or testing:

SmartAudioVisualizer visualizer = findViewById(R.id.barVisualizer);
visualizer.simulateDemoWave();