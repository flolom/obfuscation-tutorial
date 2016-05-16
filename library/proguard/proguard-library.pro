# Public API proguard rules
#

# We tell proguard to keep our public API (the endpoint of the library), so it can be used by a third
# party.
#
# The rest of the library can be obfuscated: it is our implementation, and thus can be hidden.

-keep public class com.worldline.techforum.obfuscation.api.* {
    public *;
}

-keepattributes Exceptions
-keepattributes Signature


# Implementation proguard rules


# We keep the fields of all the classes contained in the network model package:
#
# This way, gson can access by the reflection the fields of our model, and performs the serialization
# operations correcly.
#
-keepclassmembernames class com.worldline.techforum.obfuscation.api.internal.model.** {
    <fields>;
}

# we repackage all our 'implementation' classes into one and only one package
-repackageclasses com.worldline.techforum.obfuscation.api.internal