package com.github.tkmtmkt;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;

@Portable
public class BookB
{
  @PortableProperty
  private String title;

  @PortableProperty
  private String author;
}
