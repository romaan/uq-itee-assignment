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

public interface _UIHomemanagerOperations
{
    int getTemperature(Ice.Current __current);

    String getBpHeartrate(String user, Ice.Current __current);

    boolean userExists(String user, Ice.Current __current);
}
