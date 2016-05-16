
# We tell proguard to keep our public API (the endpoint of the library), so it can be used by a third
# party.
#
# The rest of the library can be obfuscated: it is our implementation, and thus can be hidden.

-keep public class com.worldline.techforum.obfuscation.api.* {
    public *;
}

-keepattributes Exceptions
-keepattributes Signature
