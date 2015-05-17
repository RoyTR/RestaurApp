using RestaurappWEB.Filters;
using RestaurappWEB.Helpers;
using RestaurappWEB.Models;
using RestaurappWEB.ViewModel.Category;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Transactions;
using System.Web;
using System.Web.Mvc;

namespace RestaurappWEB.Controllers
{
    public class CategoryController : BaseController
    {
        [AppAuthorize(AppRol.Administrador)]
        public ActionResult EditCategory(Int32? CategoryId)
        {
            var EditCategoryViewModel = new EditCategoryViewModel();
            EditCategoryViewModel.CargarDatos(CargarDatosContext(), CategoryId);
            return View(EditCategoryViewModel);
        }

        [HttpPost]
        [AppAuthorize(AppRol.Administrador)]
        public ActionResult EditCategory(EditCategoryViewModel model)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    model.CargarDatos(CargarDatosContext(), model.CategoryId);
                    TryUpdateModel(model);
                    PostMessage(MessageType.Error, i18n.ValidationStrings.DatosIncorrectos);
                    return View(model);
                }

                using (TransactionScope transaction = new TransactionScope())
                {

                    var category= new categoria();

                    if (model.CategoryId.HasValue)
                    {
                        category = context.categoria.First(x => x.id == model.CategoryId);
                    }
                    else
                    {
                        context.categoria.Add(category);
                    }

                    category.nombre = model.Nombre;
                    category.descripcion = model.Descripcion;
                    category.created_by = Session.GetUsuarioId();
                    category.updated_by = Session.GetUsuarioId();
                    category.created_at = DateTime.Now;
                    category.updated_at = DateTime.Now;
                    category.deleted_at = null;

                    context.SaveChanges();

                    PostMessage(MessageType.Success, "Se registró la categoría correctamente.");
                    transaction.Complete();

                    return RedirectToAction("ListCategory");
                }
            }
            catch (Exception ex)
            {
                InvalidarContext();
                PostMessage(MessageType.Error, "Ha ocurrido un error, por favor intentelo más tarde");
                model.CargarDatos(CargarDatosContext(), model.CategoryId);
                TryUpdateModel(model);
                return View(model);
            }
        }

        [AppAuthorize(AppRol.Administrador)]
        public ActionResult ListCategory(String Nombre, Int32? p)
        {
            var ListCategoryViewModel = new ListCategoryViewModel();
            ListCategoryViewModel.CargarDatos(CargarDatosContext(), p, Nombre);
            return View(ListCategoryViewModel);
        }

        [AppAuthorize(AppRol.Administrador)]
        public ActionResult DeleteCategory(Int32 CategoryId)
        {
            try
            {
                categoria cat = context.categoria.Find(CategoryId);
                cat.deleted_at = DateTime.Now;

                context.SaveChanges();
                PostMessage(MessageType.Success);
                return RedirectToAction("ListCategory");
            }
            catch (Exception ex)
            {
                PostMessage(MessageType.Error);
                return RedirectToAction("ListCategory");
            }
        }
    }
}
