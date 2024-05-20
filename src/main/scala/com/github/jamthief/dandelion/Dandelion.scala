package com.github.jamthief.dandelion

import scala.collection.mutable
import scala.util.Random

class Dandelion[T](
  threshold: Long
) {
  private var probability: Double = 1.0
  private val elements: mutable.Set[T] = mutable.Set()

  private val randy: Random = new Random
  private def heads: Boolean = randy.nextDouble >= 0.5

  def put(element: T): Unit = {
    if (elements.size >= threshold) {
      shedElements()
      probability = probability / 2
    }

    elements.update(element, rollSave(probability))
  }

  def estimate(): Double = elements.size.toDouble / probability

  private def shedElements(): Unit = elements.filterInPlace(_ => heads)

  private def rollSave(p: Double): Boolean = p >= randy.nextDouble
}
