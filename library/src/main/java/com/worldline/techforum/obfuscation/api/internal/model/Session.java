package com.worldline.techforum.obfuscation.api.internal.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class Session {

    public abstract String description();
    public abstract String title();
    public abstract String author();
    public abstract When when();
    public abstract String bu();
    public abstract List<String> theme();
    public abstract String _id();
    public abstract Integer where();
    public abstract String day();

    public static TypeAdapter<Session> typeAdapter(Gson gson) {
        return new AutoValue_Session.GsonTypeAdapter(gson);
    }
}
