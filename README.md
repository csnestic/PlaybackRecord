# PlaybackRecord

Designed to run via command line / terminal.

run mvn package from the source folder, jar will be packaged in target folder.

java -cp [path to packaged jar]\PlaybackRecord-1.0.jar com.mediaProject.App [args]

Values that work for [args] :

record [optional: filename] - current version will record 10 seconds of audio and create a .wav file with the given filename or call the file recording.wav if no filename is provided.

play [required: filename] - a filename must be supplied, will playback the named wav file. If only a filename is given it will check the current directory folder for the file.