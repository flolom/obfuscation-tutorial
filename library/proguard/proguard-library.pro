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

# no need to keep the network model anymore: the serialization code is now generated at the compile
# time, instead of performing class inspection at runtime


# we repackage all our 'implementation' classes into one and only one package
-repackageclasses com.worldline.techforum.obfuscation.api.internal