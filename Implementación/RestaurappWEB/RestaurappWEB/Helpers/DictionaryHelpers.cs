using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;
using System.Linq.Expressions;
using System.Web.Mvc.Html;

namespace RestaurappWEB.Helpers
{
    public static class DictionaryHelpers
    {
        public static TValue GetValueOrDefault<TKey, TValue>(this Dictionary<TKey, TValue> diccionario, TKey key, TValue defaultValue)
        {
            if (diccionario.ContainsKey(key))
                return diccionario[key];
            return defaultValue;
        }
    }
}