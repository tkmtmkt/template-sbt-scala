package com.github.tkmtmkt

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

object Main extends App
{
  val hoge = CacheFactory.getCache("hoge")
  val book = new Book
  book.title = "Scalaで学ぶ関数脳入門"
  book.author = "株式会社テクノロジックアート"
  hoge.put("ISBN978-4-7741-4436-8", book)
}
