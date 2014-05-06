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

public final class UIHomemanagerPrxHelper extends Ice.ObjectPrxHelperBase implements UIHomemanagerPrx
{
    public String
    getBpHeartrate(String user)
    {
        return getBpHeartrate(user, null, false);
    }

    public String
    getBpHeartrate(String user, java.util.Map<String, String> __ctx)
    {
        return getBpHeartrate(user, __ctx, true);
    }

    @SuppressWarnings("unchecked")
    private String
    getBpHeartrate(String user, java.util.Map<String, String> __ctx, boolean __explicitCtx)
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
                __checkTwowayOnly("getBpHeartrate");
                __delBase = __getDelegate(false);
                _UIHomemanagerDel __del = (_UIHomemanagerDel)__delBase;
                return __del.getBpHeartrate(user, __ctx);
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

    public int
    getTemperature()
    {
        return getTemperature(null, false);
    }

    public int
    getTemperature(java.util.Map<String, String> __ctx)
    {
        return getTemperature(__ctx, true);
    }

    @SuppressWarnings("unchecked")
    private int
    getTemperature(java.util.Map<String, String> __ctx, boolean __explicitCtx)
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
                __checkTwowayOnly("getTemperature");
                __delBase = __getDelegate(false);
                _UIHomemanagerDel __del = (_UIHomemanagerDel)__delBase;
                return __del.getTemperature(__ctx);
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

    public boolean
    userExists(String user)
    {
        return userExists(user, null, false);
    }

    public boolean
    userExists(String user, java.util.Map<String, String> __ctx)
    {
        return userExists(user, __ctx, true);
    }

    @SuppressWarnings("unchecked")
    private boolean
    userExists(String user, java.util.Map<String, String> __ctx, boolean __explicitCtx)
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
                __checkTwowayOnly("userExists");
                __delBase = __getDelegate(false);
                _UIHomemanagerDel __del = (_UIHomemanagerDel)__delBase;
                return __del.userExists(user, __ctx);
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

    public static UIHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj)
    {
        UIHomemanagerPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (UIHomemanagerPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::exchange::UIHomemanager"))
                {
                    UIHomemanagerPrxHelper __h = new UIHomemanagerPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static UIHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        UIHomemanagerPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (UIHomemanagerPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::exchange::UIHomemanager", __ctx))
                {
                    UIHomemanagerPrxHelper __h = new UIHomemanagerPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static UIHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        UIHomemanagerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::exchange::UIHomemanager"))
                {
                    UIHomemanagerPrxHelper __h = new UIHomemanagerPrxHelper();
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

    public static UIHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        UIHomemanagerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::exchange::UIHomemanager", __ctx))
                {
                    UIHomemanagerPrxHelper __h = new UIHomemanagerPrxHelper();
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

    public static UIHomemanagerPrx
    uncheckedCast(Ice.ObjectPrx __obj)
    {
        UIHomemanagerPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (UIHomemanagerPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                UIHomemanagerPrxHelper __h = new UIHomemanagerPrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static UIHomemanagerPrx
    uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        UIHomemanagerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            UIHomemanagerPrxHelper __h = new UIHomemanagerPrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    protected Ice._ObjectDelM
    __createDelegateM()
    {
        return new _UIHomemanagerDelM();
    }

    protected Ice._ObjectDelD
    __createDelegateD()
    {
        return new _UIHomemanagerDelD();
    }

    public static void
    __write(IceInternal.BasicStream __os, UIHomemanagerPrx v)
    {
        __os.writeProxy(v);
    }

    public static UIHomemanagerPrx
    __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            UIHomemanagerPrxHelper result = new UIHomemanagerPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }
}
