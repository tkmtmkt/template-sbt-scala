package com.github.tkmtmkt;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;

/**
 * @author takamatu
 *
 */
@Portable
public class BookB
{
    @PortableProperty
    private String title;

    @PortableProperty
    private String author;

    public String getTitle() {
        return title;
    }

    void setTitle(final String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }
}
// vim: set ts=4 sw=4 et:
