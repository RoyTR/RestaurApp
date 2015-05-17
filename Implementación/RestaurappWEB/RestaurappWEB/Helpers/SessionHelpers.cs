using RestaurappWEB.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.SessionState;

namespace RestaurappWEB.Helpers
{
    public enum AppRol
    {
        Administrador,
        Usuario
    }

    public enum SessionKey
    {
        Usuario,
        UsuarioId,
        Nombres,
        Apellidos,
        Rol
    }

    public static class SessionHelpers
    {
        #region TieneRol
        public static Boolean TieneRol(this HttpSessionState Session, AppRol Rol)
        {
            return Session.GetRol() == Rol;
        }

        public static Boolean TieneRol(this HttpSessionStateBase Session, AppRol Rol)
        {
            return Session.GetRol() == Rol;
        }


        #endregion

        #region GetUsuarioId
        public static Int32 GetUsuarioId(this HttpSessionState Session)
        {
            return Get(Session, SessionKey.UsuarioId).ToInteger();
        }

        public static Int32 GetUsuarioId(this HttpSessionStateBase Session)
        {
            return Get(Session, SessionKey.UsuarioId).ToInteger();
        }
        #endregion

        #region GetNombre
        public static String GetNombre(this HttpSessionState Session)
        {
            return Get(Session, SessionKey.Nombres).ToString();
        }

        public static String GetNombre(this HttpSessionStateBase Session)
        {
            return Get(Session, SessionKey.Nombres).ToString();
        }
        #endregion

        #region GetApellidos
        public static String GetApellidos(this HttpSessionState Session)
        {
            return Get(Session, SessionKey.Apellidos).ToString();
        }

        public static String GetApellidos(this HttpSessionStateBase Session)
        {
            return Get(Session, SessionKey.Apellidos).ToString();
        }
        #endregion

        #region GetRol
        public static AppRol? GetRol(this HttpSessionState Session)
        {
            return (AppRol?)Get(Session, SessionKey.Rol);
        }

        public static AppRol? GetRol(this HttpSessionStateBase Session)
        {
            return (AppRol?)Get(Session, SessionKey.Rol);
        }
        #endregion

        #region Private

        private static object Get(HttpSessionState Session, String Key)
        {
            return Session[Key];
        }

        private static void Set(HttpSessionState Session, String Key, object Value)
        {
            Session[Key] = Value;
        }

        private static bool Exists(HttpSessionState Session, String Key)
        {
            return Session[Key] != null;
        }

        private static object Get(HttpSessionStateBase Session, String Key)
        {
            return Session[Key];
        }

        private static void Set(HttpSessionStateBase Session, String Key, object Value)
        {
            Session[Key] = Value;
        }

        private static bool Exists(HttpSessionStateBase Session, String Key)
        {
            return Session[Key] != null;
        }

        #endregion

        #region Getters setters GlobalKey
        //HttpSessionState
        public static object Get(this HttpSessionState Session, SessionKey Key)
        {
            return Get(Session, Key.ToString());
        }

        public static void Set(this HttpSessionState Session, SessionKey Key, object Value)
        {
            Set(Session, Key.ToString(), Value);
        }

        public static bool Exists(this HttpSessionState Session, SessionKey Key)
        {
            return Exists(Session, Key.ToString());
        }

        //HttpSessionStateBase
        public static object Get(this HttpSessionStateBase Session, SessionKey Key)
        {
            return Get(Session, Key.ToString());
        }

        public static void Set(this HttpSessionStateBase Session, SessionKey Key, object Value)
        {
            Set(Session, Key.ToString(), Value);
        }

        public static bool Exists(this HttpSessionStateBase Session, SessionKey Key)
        {
            return Exists(Session, Key.ToString());
        }
        #endregion
    }
}