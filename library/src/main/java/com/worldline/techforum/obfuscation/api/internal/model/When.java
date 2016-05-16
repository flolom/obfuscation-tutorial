
package com.worldline.techforum.obfuscation.api.internal.model;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Value.Immutable
@Gson.TypeAdapters
public abstract class When {

    public abstract String start();
    public abstract String end();
}
