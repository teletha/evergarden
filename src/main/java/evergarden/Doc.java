/*
 * Copyright (C) 2025 The EVERGARDEN Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package evergarden;

import java.util.ArrayList;
import java.util.List;

/**
 * Scanned doc data.
 */
public class Doc {

    public String title;

    public String path;

    public List<Doc> subs = new ArrayList();
}