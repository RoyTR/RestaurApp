using RestaurappWEB.Filters;
using RestaurappWEB.Helpers;
using RestaurappWEB.ViewModel.Home;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using DevOne.Security.Cryptography.BCrypt;

namespace RestaurappWEB.Controllers
{
    public class HomeController : BaseController
    {
        //
        // GET: /Home/

        public ActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Login(String Codigo, String Password)
        {

            var viewModel = new LoginViewModel();

            var usuario = context.usuario.FirstOrDefault(x => x.username == Codigo && x.password == Password);


            if (usuario == null || String.IsNullOrEmpty(usuario.password) || Password != usuario.password)
            {
                PostMessage(MessageType.Error, "Usuario y/o Contraseña Incorrectos");
            }
            else
            {
                Session.Clear();

                AppRol rol = AppRol.Administrador;

                switch (usuario.is_admin)
                {
                    case ConstantHelpers.ROL_ADMINISTRADOR: rol = AppRol.Administrador; break;
                }

                Session.Set(SessionKey.Usuario, usuario);
                Session.Set(SessionKey.UsuarioId, usuario.id);
                Session.Set(SessionKey.Nombres, usuario.nombres);
                Session.Set(SessionKey.Apellidos, usuario.apellidos);
                Session.Set(SessionKey.Rol, rol);

                return Dashboard();
            }

            return RedirectToAction("Login");
        }


        [AppAuthorize(AppRol.Administrador)]
        [ViewParameter("Dashboard", "fa fa-dashboard")]
        public ActionResult AdministradorIndex()
        {
            var viewModel = new AdministradorIndexViewModel();
            viewModel.CargarDatos(CargarDatosContext());
            return View(viewModel);
        }

        public ActionResult Dashboard()
        {
            switch (Session.GetRol())
            {
                case AppRol.Administrador: return RedirectToAction("AdministradorIndex");
            }
            return RedirectToAction("Login");
        }

        public ActionResult Logout()
        {
            Session.Clear();
            return RedirectToAction("Login");
        }
    }
}
