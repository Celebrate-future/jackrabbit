/*
 * Copyright 2004-2005 The Apache Software Foundation or its licensors,
 *                     as applicable.
 *
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
 */
package org.apache.jackrabbit.core.jndi.provider;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import java.util.Hashtable;
import java.util.HashMap;

/**
 * <code>DummyInitialContextFactory</code> ...
 */
public class DummyInitialContextFactory implements InitialContextFactory {

    private static HashMap contexts = new HashMap();
    /**
     * @see InitialContextFactory#getInitialContext(java.util.Hashtable)
     */
    public Context getInitialContext(Hashtable environment) throws NamingException {

	String url = (String) environment.get(Context.PROVIDER_URL);
	if (url==null) {
	    throw new NamingException("Unable to create context. Environment is missing a " + Context.PROVIDER_URL);
	}

	synchronized (DummyInitialContextFactory.contexts) {
	    DummyContext ctx = (DummyContext) contexts.get(url);
	    if (ctx==null) {
		ctx = new DummyContext(environment);
		contexts.put(url, ctx);
	    }
	    return ctx;
	}
    }
}
