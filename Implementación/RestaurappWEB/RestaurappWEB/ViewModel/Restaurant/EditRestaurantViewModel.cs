using RestaurappWEB.Controllers;
using RestaurappWEB.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace RestaurappWEB.ViewModel.Restaurant
{
    public class EditRestaurantViewModel
    {
        public Int32? RestaurantId { get; set; }

        public List<categoria> LstCategorias { get; set; }
        public List<distrito> LstDistritos { get; set; }

        [Display(Name = "Categoría")]
        public Int32 CategoriaId { get; set; }

        [Display(Name = "Nombre")]
        [Required(ErrorMessageResourceName = "CampoRequerido", ErrorMessageResourceType = typeof(i18n.ValidationStrings))]
        public String Nombre { get; set; }

        [Display(Name = "Descripción")]
        [Required(ErrorMessageResourceName = "CampoRequerido", ErrorMessageResourceType = typeof(i18n.ValidationStrings))]
        public String Descripcion { get; set; }

        [Display(Name = "Latitud")]
        public String Latitud { get; set; }

        [Display(Name = "Longitud")]
        public String Longitud { get; set; }

        [Display(Name = "Distrito")]
        [Required(ErrorMessageResourceName = "CampoRequerido", ErrorMessageResourceType = typeof(i18n.ValidationStrings))]
        public long? DistritoId { get; set; }

        public EditRestaurantViewModel()
        {
        }

        public void CargarDatos(CargarDatosContext dataContext, Int32? RestaurantId)
        {
            LstCategorias = dataContext.context.categoria.Where(x => x.deleted_at == null).ToList();
            LstDistritos = dataContext.context.distrito.ToList();

            if (RestaurantId.HasValue)
            {
                var rest = dataContext.context.restaurante.First(x => x.id == RestaurantId);
                var cat = dataContext.context.restaurante_categoria.First(x => x.restaurante_id == RestaurantId);
 
                this.RestaurantId = Convert.ToInt32(rest.id);
                this.Nombre = rest.nombre;
                this.Descripcion = rest.descripcion;
                this.Longitud = rest.longitud;
                this.Latitud = rest.latitud;
                this.DistritoId = rest.distrito_id;
                if(cat != null)
                    this.CategoriaId = Convert.ToInt32(cat.categoria_id);
            }
        }
    }
}