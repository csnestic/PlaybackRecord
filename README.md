# PlaybackRecord

Designed to run via command line / terminal.

run mvn package from the source folder, jar will be packaged in target folder.

java -cp [path to packaged jar]\PlaybackRecord-1.1.jar com.mediaProject.App [args]

Additionally the package can be run using mvn exec in two ways, provided your terminal is in the directory with the pom.xml:

mvn clean install exec:java -Dexec.args="[args]"
OR
mvn clean install exec:exec -Dexec.executable="java" -Dexec.args="-classpath target/classes com.mediaProject.App [args]"

Values that work for [args] :

record (optional: filename) (optional: length of recording in seconds) (optional: number to select recording device)  - current version will default record 10 seconds of audio and create a .wav file with the given filename or call the file recording.wav if no filename is provided.

NOTE: Each previous argument become required in this current version. Meaning, if you wish to specify a recording device you are required to specify a string for the filename and an int for the recording length.

play (required: filename) (optional: number to select playback device) - a filename must be supplied, will playback the named wav file. If only a filename is given it will check the current directory folder for the file. 

The program indexes a list of connected playback devices, if a number is specified the audio will be played back via the respective device in the list. If no number is specified the default playback device will be used.
