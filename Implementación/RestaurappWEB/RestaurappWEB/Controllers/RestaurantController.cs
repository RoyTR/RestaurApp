using RestaurappWEB.Filters;
using RestaurappWEB.Helpers;
using RestaurappWEB.Models;
using RestaurappWEB.ViewModel.Restaurant;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Transactions;
using System.Web;
using System.Web.Mvc;

namespace RestaurappWEB.Controllers
{
    public class RestaurantController : BaseController
    {
        [AppAuthorize(AppRol.Administrador)]
        public ActionResult EditRestaurant(Int32? RestaurantId)
        {
            var EditRestaurantViewModel = new EditRestaurantViewModel();
            EditRestaurantViewModel.CargarDatos(CargarDatosContext(), RestaurantId);
            return View(EditRestaurantViewModel);
        }

        [HttpPost]
        [AppAuthorize(AppRol.Administrador)]
        public ActionResult EditRestaurant(EditRestaurantViewModel model)
        {
            try
            {
                if (!ModelState.IsValid)
                {
                    model.CargarDatos(CargarDatosContext(), model.RestaurantId);
                    TryUpdateModel(model);
                    PostMessage(MessageType.Error, i18n.ValidationStrings.DatosIncorrectos);
                    return View(model);
                }

                using (TransactionScope transaction = new TransactionScope())
                {

                    var restaurant = new restaurante();
                    var categoryrestaurant = new restaurante_categoria();

                    if (model.RestaurantId.HasValue)
                    {
                        restaurant = context.restaurante.First(x => x.id == model.RestaurantId);
                        categoryrestaurant = context.restaurante_categoria.First(x => x.restaurante_id == model.RestaurantId);
                    }
                    else
                    {
                        restaurant.nombre = model.Nombre;
                        restaurant.descripcion = model.Descripcion;
                        restaurant.latitud = model.Latitud;
                        restaurant.longitud = model.Longitud;
                        restaurant.created_by = Session.GetUsuarioId();
                        restaurant.updated_by = Session.GetUsuarioId();
                        restaurant.created_at = DateTime.Now;
                        restaurant.updated_at = DateTime.Now;
                        restaurant.deleted_at = null;
                        restaurant.distrito_id = model.DistritoId;
                        context.restaurante.Add(restaurant);

                        context.SaveChanges();

                        categoryrestaurant.restaurante_id = restaurant.id;
                        categoryrestaurant.categoria_id = model.CategoriaId;
                        context.SaveChanges();
                        
                        context.restaurante_categoria.Add(categoryrestaurant);
                    }

                    restaurant.nombre = model.Nombre;
                    restaurant.descripcion = model.Descripcion;
                    restaurant.latitud = model.Latitud;
                    restaurant.longitud = model.Longitud;
                    restaurant.created_by = Session.GetUsuarioId();
                    restaurant.updated_by = Session.GetUsuarioId();
                    restaurant.created_at = DateTime.Now;
                    restaurant.updated_at = DateTime.Now;
                    restaurant.deleted_at = null;
                    restaurant.distrito_id = model.DistritoId;
                    context.SaveChanges();

                    categoryrestaurant.restaurante_id = restaurant.id;
                    categoryrestaurant.categoria_id = model.CategoriaId;
                    context.SaveChanges();

                    PostMessage(MessageType.Success, "Se registró el restaurante correctamente.");
                    transaction.Complete();

                    return RedirectToAction("ListRestaurant");
                }
            }
            catch (Exception ex)
            {
                InvalidarContext();
                PostMessage(MessageType.Error, "Ha ocurrido un error, por favor intentelo más tarde");
                model.CargarDatos(CargarDatosContext(), model.RestaurantId);
                TryUpdateModel(model);
                return View(model);
            }
        }

        [AppAuthorize(AppRol.Administrador)]
        public ActionResult ListRestaurant(String Nombre, Int32? p)
        {
            var ListRestaurantViewModel = new ListRestaurantViewModel();
            ListRestaurantViewModel.CargarDatos(CargarDatosContext(), p, Nombre);
            return View(ListRestaurantViewModel);
        }

        [AppAuthorize(AppRol.Administrador)]
        public ActionResult DeleteRestaurant(Int32 RestaurantId)
        {
            try
            {
                restaurante rest = context.restaurante.Find(RestaurantId);
                rest.deleted_at = DateTime.Now;

                context.SaveChanges();
                PostMessage(MessageType.Success);
                return RedirectToAction("ListRestaurant");
            }
            catch (Exception ex)
            {
                PostMessage(MessageType.Error);
                return RedirectToAction("ListRestaurant");
            }
        }
    }
}
