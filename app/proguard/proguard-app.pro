
# We keep the fields of all the classes contained in the network model package:
#
# This way, gson can access by the reflection the fields of our model, and performs the serialization
# operations correcly.
#
-keepclassmembernames class com.worldline.techforum.obfuscation.api.internal.model.** {
    <fields>;
}