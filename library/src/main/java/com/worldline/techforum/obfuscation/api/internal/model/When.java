
package com.worldline.techforum.obfuscation.api.internal.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class When {

    public abstract String start();
    public abstract String end();

    public static TypeAdapter<When> typeAdapter(Gson gson) {
        return new AutoValue_When.GsonTypeAdapter(gson);
    }
}
