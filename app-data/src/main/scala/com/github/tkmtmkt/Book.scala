package com.github.tkmtmkt

import com.tangosol.io.pof.annotation.Portable
import com.tangosol.io.pof.annotation.PortableProperty

@SerialVersionUID(123L)
@Portable
class Book extends Serializable
{
  @PortableProperty(1)
  var title:String = _

  @PortableProperty(2)
  var author:String = _
}
