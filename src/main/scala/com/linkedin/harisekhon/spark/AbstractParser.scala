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

package com.linkedin.nholuongut.spark

import java.lang.Long
import java.util.HashMap
import java.util.ArrayList

abstract class AbstractParser extends Serializable {
  def parse(path: String, offset: Long, line: String): AnyRef // HashMap[String, String]
  def returns(): AnyRef
}
