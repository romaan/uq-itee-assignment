// **********************************************************************
//
// Copyright (c) 2003-2009 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************

// Ice version 3.3.1

package exchange;

public interface BpheartrateHomemanagerPrx extends Ice.ObjectPrx
{
    public void bpHeartrateData(String data, String user);
    public void bpHeartrateData(String data, String user, java.util.Map<String, String> __ctx);
}
