using RestaurappWEB.Filters;
using RestaurappWEB.Helpers;
using RestaurappWEB.Models;
using RestaurappWEB.ViewModel.User;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Transactions;
using System.Web;
using System.Web.Mvc;

namespace RestaurappWEB.Controllers
{
    public class UserController : BaseController
    {
        [AppAuthorize(AppRol.Administrador)]
        public ActionResult EditUser(Int32? UserId)
        {
            var EditUserViewModel = new EditUserViewModel();
            EditUserViewModel.CargarDatos(CargarDatosContext(), UserId);
            return View(EditUserViewModel);
        }

        [HttpPost]
        [AppAuthorize(AppRol.Administrador)]
        public ActionResult EditUser(EditUserViewModel model)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    model.CargarDatos(CargarDatosContext(), model.UserId);
                    TryUpdateModel(model);
                    PostMessage(MessageType.Error, i18n.ValidationStrings.DatosIncorrectos);
                    return View(model);
                }

                using (TransactionScope transaction = new TransactionScope())
                {

                    var user = new usuario();

                    if (model.UserId.HasValue)
                    {
                        user = context.usuario.First(x => x.id == model.UserId);
                    }
                    else
                    {
                        context.usuario.Add(user);
                    }

                    user.nombres = model.Nombre;
                    user.apellidos = model.Apellidos;
                    user.username = model.Usuario;
                    user.email = model.Email;
                    user.password = model.Password;
                    user.facebook_id = null;
                    user.is_admin = "Yes";
                    user.access_token = null;
                    user.created_by = Session.GetUsuarioId();
                    user.updated_by = Session.GetUsuarioId();
                    user.created_at = DateTime.Now;
                    user.updated_at = DateTime.Now;
                    user.deleted_at = null;

                    context.SaveChanges();

                    PostMessage(MessageType.Success, "Se registró el usuario correctamente.");
                    transaction.Complete();

                    return RedirectToAction("ListUser");
                }
            }
            catch (Exception ex)
            {
                InvalidarContext();
                PostMessage(MessageType.Error, "Ha ocurrido un error, por favor intentelo más tarde");
                model.CargarDatos(CargarDatosContext(), model.UserId);
                TryUpdateModel(model);
                return View(model);
            }
        }

        [AppAuthorize(AppRol.Administrador)]
        public ActionResult ListUser(String Nombre, Int32? p)
        {
            var ListUserViewModel = new ListUserViewModel();
            ListUserViewModel.CargarDatos(CargarDatosContext(), p, Nombre);
            return View(ListUserViewModel);
        }

        [AppAuthorize(AppRol.Administrador)]
        public ActionResult DeleteUser(Int32 UserId)
        {
            try
            {
                usuario user = context.usuario.Find(UserId);
                user.deleted_at = DateTime.Now;

                context.SaveChanges();
                PostMessage(MessageType.Success);
                return RedirectToAction("ListUser");
            }
            catch (Exception ex)
            {
                PostMessage(MessageType.Error);
                return RedirectToAction("ListUser");
            }
        }
    }
}
