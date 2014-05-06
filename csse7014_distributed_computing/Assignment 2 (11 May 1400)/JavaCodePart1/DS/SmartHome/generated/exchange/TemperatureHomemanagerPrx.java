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
// Generated from file `TemperatureHomemanagerPrx.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package exchange;

public interface TemperatureHomemanagerPrx extends Ice.ObjectPrx
{
    public void temperatureData(int data);

    public void temperatureData(int data, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_temperatureData(int data);

    public Ice.AsyncResult begin_temperatureData(int data, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_temperatureData(int data, Ice.Callback __cb);

    public Ice.AsyncResult begin_temperatureData(int data, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_temperatureData(int data, Callback_TemperatureHomemanager_temperatureData __cb);

    public Ice.AsyncResult begin_temperatureData(int data, java.util.Map<String, String> __ctx, Callback_TemperatureHomemanager_temperatureData __cb);

    public void end_temperatureData(Ice.AsyncResult __result);
}
