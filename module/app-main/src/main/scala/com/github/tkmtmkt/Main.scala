package com.github.tkmtmkt

import scala.collection.immutable.Map

object Main extends App
{
  val book = new Book
  book.title = "Scalaで学ぶ関数脳入門"
  book.author = "株式会社テクノロジックアート"

  val map = Map("ISBN978-4-7741-4436-8" -> book)
}
