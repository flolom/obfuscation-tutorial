package com.worldline.techforum.obfuscation.api.internal.model;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@Gson.TypeAdapters
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
}
