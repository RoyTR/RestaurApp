using PagedList;
using RestaurappWEB.Controllers;
using RestaurappWEB.Helpers;
using RestaurappWEB.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RestaurappWEB.ViewModel.Restaurant
{
    public class ListRestaurantViewModel
    {
        public Int32 p { get; set; }
        public IPagedList<restaurante> LstRestaurant { get; set; }
        public List<restaurante_categoria> LstRestCategoria { get; set; }
        public String Nombre { get; set; }

        public ListRestaurantViewModel()
        {
        }

        public void CargarDatos(CargarDatosContext dataContext, Int32? p, String Nombre)
        {
            this.p = p ?? 1;
            IQueryable<restaurante> queryRestaurant = dataContext.context.restaurante.AsQueryable();
            queryRestaurant = queryRestaurant.Where(x => x.deleted_at == null);

            if (Nombre != null)
                queryRestaurant = queryRestaurant.Where(x => x.nombre.Equals(Nombre));

            queryRestaurant = queryRestaurant.OrderBy(x => x.nombre);
            LstRestaurant = queryRestaurant.ToPagedList(this.p, ConstantHelpers.DEFAULT_PAGE_SIZE);

            LstRestCategoria = dataContext.context.restaurante_categoria.ToList();
        }
    }
}