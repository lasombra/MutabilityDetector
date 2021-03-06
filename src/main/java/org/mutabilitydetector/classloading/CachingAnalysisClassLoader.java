package org.mutabilitydetector.classloading;

/*
 * #%L
 * MutabilityDetector
 * %%
 * Copyright (C) 2008 - 2014 Graham Allan
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;

public final class CachingAnalysisClassLoader implements AnalysisClassLoader {
    
    private final Cache<String, Class<?>> cache = CacheBuilder.newBuilder().recordStats().build();
    private final AnalysisClassLoader classLoader;
    
    public CachingAnalysisClassLoader(AnalysisClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Class<?> loadClass(final String dottedClass) throws ClassNotFoundException {
        try {
            return cache.get(dottedClass, () -> classLoader.loadClass(dottedClass));
        } catch (ExecutionException e) {
            throw new ClassNotFoundException("Error loading class: " + dottedClass, e.getCause());
        }
    }

}
