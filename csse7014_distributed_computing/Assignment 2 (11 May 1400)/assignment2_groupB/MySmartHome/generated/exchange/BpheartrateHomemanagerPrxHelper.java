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

public final class BpheartrateHomemanagerPrxHelper extends Ice.ObjectPrxHelperBase implements BpheartrateHomemanagerPrx
{
    public void
    bpHeartrateData(String data, String user)
    {
        bpHeartrateData(data, user, null, false);
    }

    public void
    bpHeartrateData(String data, String user, java.util.Map<String, String> __ctx)
    {
        bpHeartrateData(data, user, __ctx, true);
    }

    @SuppressWarnings("unchecked")
    private void
    bpHeartrateData(String data, String user, java.util.Map<String, String> __ctx, boolean __explicitCtx)
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
                _BpheartrateHomemanagerDel __del = (_BpheartrateHomemanagerDel)__delBase;
                __del.bpHeartrateData(data, user, __ctx);
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

    public static BpheartrateHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj)
    {
        BpheartrateHomemanagerPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (BpheartrateHomemanagerPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::exchange::BpheartrateHomemanager"))
                {
                    BpheartrateHomemanagerPrxHelper __h = new BpheartrateHomemanagerPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static BpheartrateHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        BpheartrateHomemanagerPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (BpheartrateHomemanagerPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                if(__obj.ice_isA("::exchange::BpheartrateHomemanager", __ctx))
                {
                    BpheartrateHomemanagerPrxHelper __h = new BpheartrateHomemanagerPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static BpheartrateHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        BpheartrateHomemanagerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::exchange::BpheartrateHomemanager"))
                {
                    BpheartrateHomemanagerPrxHelper __h = new BpheartrateHomemanagerPrxHelper();
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

    public static BpheartrateHomemanagerPrx
    checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        BpheartrateHomemanagerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA("::exchange::BpheartrateHomemanager", __ctx))
                {
                    BpheartrateHomemanagerPrxHelper __h = new BpheartrateHomemanagerPrxHelper();
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

    public static BpheartrateHomemanagerPrx
    uncheckedCast(Ice.ObjectPrx __obj)
    {
        BpheartrateHomemanagerPrx __d = null;
        if(__obj != null)
        {
            try
            {
                __d = (BpheartrateHomemanagerPrx)__obj;
            }
            catch(ClassCastException ex)
            {
                BpheartrateHomemanagerPrxHelper __h = new BpheartrateHomemanagerPrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static BpheartrateHomemanagerPrx
    uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        BpheartrateHomemanagerPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            BpheartrateHomemanagerPrxHelper __h = new BpheartrateHomemanagerPrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    protected Ice._ObjectDelM
    __createDelegateM()
    {
        return new _BpheartrateHomemanagerDelM();
    }

    protected Ice._ObjectDelD
    __createDelegateD()
    {
        return new _BpheartrateHomemanagerDelD();
    }

    public static void
    __write(IceInternal.BasicStream __os, BpheartrateHomemanagerPrx v)
    {
        __os.writeProxy(v);
    }

    public static BpheartrateHomemanagerPrx
    __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            BpheartrateHomemanagerPrxHelper result = new BpheartrateHomemanagerPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }
}
