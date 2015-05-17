using RestaurappWEB.Controllers;
using RestaurappWEB.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RestaurappWEB.ViewModel.Home
{
    public class AdministradorIndexViewModel
    {
        public List<usuario> LstUsuarios { get; set; }
        public List<restaurante> LstRestaurantes { get; set; }
        public Int32 Usuarios { get; set; }
        public Int32 Restaurantes { get; set; }
        public Int32 Recomendaciones { get; set; }
        public Int32 Categorias { get; set; }

        public void CargarDatos(CargarDatosContext datacontext)
        {
            LstUsuarios = datacontext.context.usuario.OrderByDescending(x => x.created_at).Take(10).ToList();
            Usuarios = LstUsuarios.Count();

            LstRestaurantes = datacontext.context.restaurante.OrderByDescending(x => x.created_at).Take(10).ToList();
            Restaurantes = LstRestaurantes.Count();

            var query = datacontext.context.categoria.ToList();
            Categorias = query.Count();
        }
    }
}