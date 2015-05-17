using PagedList;
using RestaurappWEB.Controllers;
using RestaurappWEB.Helpers;
using RestaurappWEB.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RestaurappWEB.ViewModel.User
{
    public class ListUserViewModel
    {
        public Int32 p { get; set; }
        public IPagedList<usuario> LstUser { get; set; }
        public String Nombre { get; set; }

        public ListUserViewModel()
        {
        }

        public void CargarDatos(CargarDatosContext dataContext, Int32? p, String Nombre)
        {
            this.p = p ?? 1;
            IQueryable<usuario> queryUsuario = dataContext.context.usuario.AsQueryable();
            queryUsuario = queryUsuario.Where(x => x.deleted_at == null);

            if (Nombre != null)
                queryUsuario = queryUsuario.Where(x => x.username.Equals(Nombre));

            queryUsuario = queryUsuario.OrderBy(x => x.nombres);
            LstUser = queryUsuario.ToPagedList(this.p, ConstantHelpers.DEFAULT_PAGE_SIZE);
        }
    }
}