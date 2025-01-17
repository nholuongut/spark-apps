//
//  Author: Nho Luong
//  Date: 2015-05-25 23:27:15 +0100 (Mon, 25 May 2015)
//
//  vim:ts=4:sts=4:sw=4:et
//
//  https://github.com/nholuongut/spark-apps
//
//  License: see accompanying nholuongut LICENSE file
//
//  If you're using my code you're welcome to connect with me on LinkedIn and optionally send me feedback to help improve or steer this or other code I publish
//
//  https://www.linkedin.com/in/nholuong
//

// https://github.com/sbt/sbt-assembly

import AssemblyKeys._

assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("META-INF", "maven","org.slf4j","slf4j-api", p) if p.startsWith("pom")        => MergeStrategy.discard
    case PathList("META-INF", "maven","commons-lang","commons-lang", p) if p.startsWith("pom")  => MergeStrategy.discard
    case PathList("com", "esotericsoftware", "minlog", p)         if p.startsWith("Log")        => MergeStrategy.first
    // too many things here condensed down to just dedupe all
    case PathList("com", "google", "common", "base", p)                                         => MergeStrategy.first
    //case PathList("org", "apache", "commons", "beanutils", p)                                 => MergeStrategy.first
    //case PathList("org", "apache", "commons", "beanutils", "converters", p)                   => MergeStrategy.first
    //case PathList("org", "apache", "commons", "beanutils", xs @ _*)                           => MergeStrategy.first
    case PathList("org", "apache", "commons", p @ _*)                                           => MergeStrategy.first
    // startsWith / endsWith doesn't work for this one
    case PathList("org", "apache", "hadoop", p @ _*)    if p.contains("package-info.class")     => MergeStrategy.first
    case PathList("org", "apache", "spark", "unused", p @ _*)                                   => MergeStrategy.first
    case x => old(x)
  }
}
