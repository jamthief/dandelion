package com.github.jamthief.dandelion

import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    val source = Source.fromFile("src/main/resources/warandpeace.txt")
    val startRegex = "\\*\\*\\* START OF THE PROJECT GUTENBERG EBOOK WAR AND PEACE \\*\\*\\*".r
    val endRegex = "\\*\\*\\* END OF THE PROJECT GUTENBERG EBOOK WAR AND PEACE \\*\\*\\*".r
    val pattern = s"(?s)(?<=${startRegex.toString()}).*?(?=${endRegex.toString()})".r
    val content = pattern.findFirstIn(source.mkString).getOrElse("")
    val words = content.split("\\W+").toSeq

    val actualCounter = new Dandelion[String](words.size + 1)
    val guesstimator = new Dandelion[String](1000)

    for (word <- words) {
      actualCounter.put(word.strip.toLowerCase)
      guesstimator.put(word.strip.toLowerCase)
    }

    val actual = actualCounter.estimate().toInt
    val estimate = guesstimator.estimate()

    printf("The text has %d words, of which exactly %d are distinct words\n", words.size, actual)
    printf("Dandelion thinks there are %.2f distinct words in the text\n", estimate)
    printf("Variance was %.2f%%", 100 - ((actual / estimate) * 100))
  }
}