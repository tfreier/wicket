/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.request.target.coding;

import java.math.BigDecimal;

import org.apache.wicket.IRequestHandler;
import org.apache.wicket.WicketTestCase;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.ng.request.component.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests for {@link QueryStringUrlCodingStrategy}
 * 
 * @author <a href="mailto:jbq@apache.org">Jean-Baptiste Quenot</a>
 */
public class QueryStringUrlCodingStrategyTest extends WicketTestCase
{
	private static final Logger log = LoggerFactory.getLogger(QueryStringUrlCodingStrategyTest.class);

	/**
	 * Tests mounting.
	 */
	public void testQS()
	{
		IRequestTargetUrlCodingStrategy ucs = new QueryStringUrlCodingStrategy("/mount/point",
			TestPage.class);
		PageParameters params = new PageParameters();
		params.add("a", "1");
		params.add("a", "2");
		params.add("b", "1");
		IRequestHandler rt = new BookmarkablePageRequestTarget(TestPage.class, params);
		String path = ucs.encode(rt).toString();
		log.debug(path);
		assertEquals("mount/point?a=1&a=2&b=1", path);
	}

	/**
	 * 
	 */
	public void testNonStringParams()
	{
		final IRequestTargetUrlCodingStrategy strategy = new QueryStringUrlCodingStrategy("/foo",
			WebPage.class);
		final PageParameters params = new PageParameters();
		params.put("param1", 1);
		params.put("param2", new BigDecimal("2.0"));
		final String actual = strategy.encode(
			new BookmarkablePageRequestTarget(WebPage.class, params)).toString();
		assertEquals("foo?param1=1&param2=2.0", actual);
	}

}
