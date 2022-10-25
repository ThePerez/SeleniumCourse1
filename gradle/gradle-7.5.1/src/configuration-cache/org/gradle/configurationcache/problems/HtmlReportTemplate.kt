/*
 * Copyright 2021 the original author or authors.
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

package org.gradle.configurationcache.problems

import java.io.BufferedReader
import java.net.URL


internal
object HtmlReportTemplate {

    const val reportHtmlFileName = "configuration-cache-report.html"

    private
    const val modelLine = """<script type="text/javascript" src="configuration-cache-report-data.js"></script>"""

    /**
     * Returns the header and footer of the html template as a pair.
     */
    fun load(): Pair<String, String> {
        val template = readHtmlTemplate()
        val headerEnd = template.indexOf(modelLine)
        require(headerEnd > 0) {
            "Invalid configuration cache report template!"
        }
        val header = template.substring(0, headerEnd)
        val footer = template.substring(headerEnd + modelLine.length + 1)
        return header to footer
    }

    private
    fun readHtmlTemplate() =
        ConfigurationCacheReport::class.java
            .requireResource(reportHtmlFileName)
            .openStream()
            .bufferedReader()
            .use(BufferedReader::readText)
}


private
fun Class<*>.requireResource(path: String): URL = getResource(path).let { url ->
    require(url != null) { "Resource `$path` could not be found!" }
    url
}
