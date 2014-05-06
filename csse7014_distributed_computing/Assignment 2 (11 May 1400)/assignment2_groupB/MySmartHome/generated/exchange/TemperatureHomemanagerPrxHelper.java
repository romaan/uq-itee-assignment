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

public final class TemperatureHomemanagerPrxHelper extends Ice.ObjectPrxHelperBase implements TemperatureHomemanagerPrx
{
    public void
    temperatureData(int data)
    {
        temperatureData(data, null, false);
    }

    public void
    temperatureData(int data, java.util.Map<String, String> __ctx)
    {
        temperatureData(data, __ctx, true);
    }

    @SuppressWarnings("unchecked")
    private void
    temperatureData(int data, java.util.Map<String, String> __ctx, boolean __explicitCtx)
    {
        if(__explicitCtx && __ctx == null)
        {
            __ctx = _emptyContext;
        }
        int __cnt = 0;
        while(true)
        {
            Ice._ObjectDel __delBase = null;
            try
            {
                __delBase = __getDelegate(false);
                _TemperatureHomemanagerDel __del = (_TemperatureHomemanagerDel)__delBase;
                __del.temperatureData(data, __ctx);
                return;
            }
            catch(IceInternal.LocalExceptionWrapper __ex)
            {
                __handleExceptionWrapper(__delBase, __ex, null);
            }
            catch(Ice.LocalException __ex)
            {
                __cnt = __handleException(__delBase, __ex, null, __cnt);
            }
        }
    }

    public static TemperatureHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj)
    {
        TemperatureHomemanagerPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (TemperatureHomemanagerPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::exchange::TemperatureHomemanager"))
                {
                    TemperatureHomemanagerPrxHelper __h = new TemperatureHomemanagerPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static TemperatureHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        TemperatureHomemanagerPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (TemperatureHomemanagerPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::exchange::TemperatureHomemanager", __ctx))
                {
                    TemperatureHomemanagerPrxHelper __h = new TemperatureHomemanagerPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static TemperatureHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        TemperatureHomemanagerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::exchange::TemperatureHomemanager"))
                {
                    TemperatureHomemanagerPrxHelper __h = new TemperatureHomemanagerPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static TemperatureHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        TemperatureHomemanagerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::exchange::TemperatureHomemanager", __ctx))
                {
                    TemperatureHomemanagerPrxHelper __h = new TemperatureHomemanagerPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static TemperatureHomemanagerPrx
    uncheckedCast(Ice.ObjectPrx __obj)
    {
        TemperatureHomemanagerPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (TemperatureHomemanagerPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                TemperatureHomemanagerPrxHelper __h = new TemperatureHomemanagerPrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static TemperatureHomemanagerPrx
    uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        TemperatureHomemanagerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            TemperatureHomemanagerPrxHelper __h = new TemperatureHomemanagerPrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    protected Ice._ObjectDelM
    __createDelegateM()
    {
        return new _TemperatureHomemanagerDelM();
    }

    protected Ice._ObjectDelD
    __createDelegateD()
    {
        return new _TemperatureHomemanagerDelD();
    }

    public static void
    __write(IceInternal.BasicStream __os, TemperatureHomemanagerPrx v)
    {
        __os.writeProxy(v);
    }

    public static TemperatureHomemanagerPrx
    __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            TemperatureHomemanagerPrxHelper result = new TemperatureHomemanagerPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }
}
