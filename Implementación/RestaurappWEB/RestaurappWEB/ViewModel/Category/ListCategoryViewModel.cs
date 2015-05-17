using PagedList;
using RestaurappWEB.Controllers;
using RestaurappWEB.Helpers;
using RestaurappWEB.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RestaurappWEB.ViewModel.Category
{
    public class ListCategoryViewModel
    {
        public Int32 p { get; set; }
        public IPagedList<categoria> LstCategory { get; set; }
        public String Nombre { get; set; }

        public ListCategoryViewModel()
        {
        }

        public void CargarDatos(CargarDatosContext dataContext, Int32? p, String Nombre)
        {
            this.p = p ?? 1;
            IQueryable<categoria> queryCategoria = dataContext.context.categoria.AsQueryable();
            queryCategoria = queryCategoria.Where(x => x.deleted_at == null);

            if (Nombre != null)
                queryCategoria = queryCategoria.Where(x => x.nombre.Equals(Nombre));

            queryCategoria = queryCategoria.OrderBy(x => x.nombre);
            LstCategory = queryCategoria.ToPagedList(this.p, ConstantHelpers.DEFAULT_PAGE_SIZE);
        }
    }
}