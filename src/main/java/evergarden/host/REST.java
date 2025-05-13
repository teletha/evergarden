/*
 * Copyright (C) 2025 Nameless Production Committee
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://opensource.org/licenses/mit-license.php
 */
package evergarden.host;

import java.io.Serializable;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import kiss.I;
import kiss.JSON;
import kiss.Managed;
import kiss.Singleton;
import kiss.Storable;

/**
 * A simple REST response cache that persists data locally using {@link Storable}.
 * 
 * <p>
 * This class retrieves remote JSON data via {@code I.json(String)}, and caches
 * the result for a fixed period (1 hour). Cached data is stored as Base64-encoded
 * serialized objects. The cache is automatically restored on initialization.
 * </p>
 */
@Managed(Singleton.class)
public class REST implements Storable<REST> {

    /** Internal cache for storing URL-mapped JSON responses. */
    @Managed
    private Map<String, Item> cache = new HashMap();

    /** Private constructor for managed singleton. Restores previously stored state. */
    private REST() {
        restore();
    }

    /**
     * Fetches JSON data from the given URL, using a 1-hour cache expiration policy.
     * 
     * @param url the URL to fetch data from
     * @return the fetched or cached {@link JSON} object
     */
    public JSON data(String url) {
        Item item = cache.get(url);
        long now = System.currentTimeMillis();

        if (item == null || now - item.lastAccessTime >= 1000 * 60 * 60) {
            JSON json = I.json(url);
            item = new Item(now, Base64.getEncoder().encodeToString(json.toString().getBytes()));
            cache.put(url, item);
            store();
        }

        return I.json(new String(Base64.getDecoder().decode(item.data)));
    }

    /**
     * A record representing a single cache entry, containing timestamp and serialized data.
     * 
     * @param lastAccessTime timestamp of last access (used for expiration)
     * @param data Base64-encoded serialized JSON object
     */
    record Item(long lastAccessTime, String data) implements Serializable {
    }
}
