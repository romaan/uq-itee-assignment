// **********************************************************************
//
// Copyright (c) 2003-2011 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.4.2
//
// <auto-generated>
//
// Generated from file `_UIHomemanagerDel.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package exchange;

public interface _UIHomemanagerDel extends Ice._ObjectDel
{
    int getTemperature(java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper;

    String getBpHeartrate(String user, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper;

    boolean userExists(String user, java.util.Map<String, String> __ctx)
        throws IceInternal.LocalExceptionWrapper;
}
