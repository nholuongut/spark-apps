//
//  Author: Nho Luong
//  Date: 2015-05-25 19:39:56 +0100 (Mon, 25 May 2015)
//
//  vim:ts=2:sts=2:sw=2:et
//
//  https://github.com/nholuongut/spark-apps
//
//  License: see accompanying nholuongut LICENSE file
//
//  If you're using my code you're welcome to connect with me on LinkedIn and optionally send me feedback to help improve or steer this or other code I publish
//
//  https://www.linkedin.com/in/nholuong
//

// Parser for TextToElasticsearch (generic Spark port of my Pig => Elasticsearch unstructured
// text / log files Hadoop job originally from https://github.com/nholuongut/toolbox)

package com.linkedin.nholuongut.spark

import com.linkedin.nholuongut.Utils._
import java.lang.Long
import java.util.HashMap
import java.util.ArrayList


// Parser class to be called in TextToElasticsearch
@SerialVersionUID(100L)
class Parser extends AbstractParser {
  //def parse(path: String, offset: Long, line: String): HashMap[String, String] = {
	def parse(path: String, offset: Long, line: String): AnyRef = { // using AnyRef to allow overriding in ParserNoOffset
    val path_stripped = strip_scheme_host(path)
    // TODO: add DateLineParser logic here
    val date: String = null
    val doc = new HashMap[String, String]()
    /*
    doc.put("path", path_stripped)
    
    if (offset > -1) {
      doc.put("offset", offset.toString())
    }
    doc.put("line", line)
    if (date != null) {
      doc.put("date", date.toString())
    }
    doc
    */
    new FileOffsetLineDocument(path_stripped, offset, line)
  }

  /* replaced by utils lib
  def strip_file_scheme(path: String) = {
    val path2 = //if (path.isEmpty()) {
      //  ""
      //} else {
      path.replaceFirst("^file:(?://)?/", "/").replaceFirst("^hdfs:\\/\\/(?:[\\w.-]+(?::\\d+)?)?/", "/")
    //}
    path2
  }
  */

  // return a list of possible return objects to pass to Kryo registration for optimization
  def returns(): AnyRef = {
    /*
    val a = new ArrayList[AnyRef]();
    a.add(FileOffsetLineDocument("path", 0L, "line"))
    a.add(FileOffsetDateLineDocument("path", 0L, "line"))
    a
    */
    FileOffsetLineDocument("path", 0L, "line")
  }
}
