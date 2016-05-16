
# repackage all the classes of the application in the default package
-repackageclasses

# remove logs
-assumenosideeffects class android.util.Log { *; }
-assumenosideeffects class timber.log.Timber { *; }