# the library is shipped with its classes already obfuscated and repackaged
#
# All our implementation classes are already in the 'internal' package, so we need that the application
# using our sdk keeps the names of the classes in this package, to let gson (de)serialize our network model
# correctly

-keepclassmembernames class com.worldline.techforum.obfuscation.api.internal.* {
    <fields>;
}