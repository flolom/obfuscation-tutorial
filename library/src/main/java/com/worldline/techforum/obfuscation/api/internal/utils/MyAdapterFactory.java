package com.worldline.techforum.obfuscation.api.internal.utils;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by renaud on 06/02/17.
 */
@GsonTypeAdapterFactory
public abstract class MyAdapterFactory implements TypeAdapterFactory {
    // Static factory method to access the package
    // private generated implementation
    public static TypeAdapterFactory create() {
        return new AutoValueGson_MyAdapterFactory();
    }
}