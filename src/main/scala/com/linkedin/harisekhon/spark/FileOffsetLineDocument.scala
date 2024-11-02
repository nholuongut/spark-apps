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

package com.linkedin.nholuongut.spark

import java.lang.Long
import org.apache.commons.lang3.builder.ToStringBuilder

// Elasticsearch document
//@SerialVersionUID(101L)
// only serializes in ESHadoop if I make it a case class again
case class FileOffsetLineDocument (path: String, offset: Long, line: String)
  extends ElasticsearchDocument
    //with Serializable
  {

  /*
  override def toString(): String = {
   return ToStringBuilder.reflectionToString(this)
  }
  */
  
}
