/*
   Copyright 2012-2019 Michael Pozhidaev <msp@luwrain.org>

   This file is part of LUWRAIN.

   LUWRAIN is free software; you can redistribute it and/or
   modify it under the terms of the GNU General Public
   License as published by the Free Software Foundation; either
   version 3 of the License, or (at your option) any later version.

   LUWRAIN is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   General Public License for more details.
*/


package org.luwrain.popups;

import java.util.*;

import org.luwrain.core.*;
import org.luwrain.controls.*;
import org.luwrain.io.*;

public class WebSearchResultPopup extends ListPopupBase implements ListArea.ClickHandler
{
    protected final WebSearchResult webSearchResult;
    protected WebSearchResult.Item result = null;

    public WebSearchResultPopup(Luwrain luwrain, String name, WebSearchResult webSearchResult, Set<Popup.Flags> popupFlags)
    {
	super(luwrain, createParams(luwrain, name, webSearchResult), popupFlags);
	this.webSearchResult = webSearchResult;
		setListClickHandler(this);
    }

    public WebSearchResult.Item result()
    {
		return result;
    }

    	@Override public boolean onListClick(ListArea area, int index, Object item)
    {
	if (item == null || !(item instanceof WebSearchResult.Item))
	    return false;
	this.result = (WebSearchResult.Item)item;
	return this.closing.doOk();
    }

    static protected ListArea.Params createParams(Luwrain luwrain, String name, WebSearchResult res)
    {
	NullCheck.notNull(luwrain, "luwrain");
	NullCheck.notNull(name, "name");
	NullCheck.notNull(res, "res");
	final ListArea.Params params = new ListArea.Params();
	params.context = new DefaultControlContext(luwrain);
	params.flags = EnumSet.of(ListArea.Flags.EMPTY_LINE_TOP);
	params.name = name;
	params.model = new ListUtils.FixedModel(createListItems(res));
	params.appearance = new Appearance(params.context);
	return params;
    }

    static protected Object[] createListItems(WebSearchResult res)
    {
	NullCheck.notNull(res, "res");
	final List r = new LinkedList();
	for(WebSearchResult.Item i: res.getItems())
	{
	    r.add(i);
	    	    r.add(i.getDisplayUrl());
	    r.add(i.getSnippet());
	}
	return r.toArray(new Object[r.size()]);
    }

    static protected class Appearance extends ListUtils.DoubleLevelAppearance
    {
	public Appearance(ControlContext context)
	{
	    super(context);
	}
	@Override public boolean isSectionItem(Object obj)
	{
	    NullCheck.notNull(obj, "obj");
	    return obj instanceof WebSearchResult.Item;
	}
    }
}
