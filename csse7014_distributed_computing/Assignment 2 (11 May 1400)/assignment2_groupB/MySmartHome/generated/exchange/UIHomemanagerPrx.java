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

public interface UIHomemanagerPrx extends Ice.ObjectPrx
{
    public int getTemperature();
    public int getTemperature(java.util.Map<String, String> __ctx);

    public String getBpHeartrate(String user);
    public String getBpHeartrate(String user, java.util.Map<String, String> __ctx);

    public boolean userExists(String user);
    public boolean userExists(String user, java.util.Map<String, String> __ctx);
}
