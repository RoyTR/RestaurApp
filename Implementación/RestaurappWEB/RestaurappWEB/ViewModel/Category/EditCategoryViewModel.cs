using RestaurappWEB.Controllers;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace RestaurappWEB.ViewModel.Category
{
    public class EditCategoryViewModel
    {
        public Int32? CategoryId { get; set; }

        [Display(Name = "Nombre")]
        [Required(ErrorMessageResourceName = "CampoRequerido", ErrorMessageResourceType = typeof(i18n.ValidationStrings))]
        public String Nombre { get; set; }

        [Display(Name = "Descripción")]
        [Required(ErrorMessageResourceName = "CampoRequerido", ErrorMessageResourceType = typeof(i18n.ValidationStrings))]
        public String Descripcion { get; set; }

        public EditCategoryViewModel()
        {
        }

        public void CargarDatos(CargarDatosContext dataContext, Int32? CategoryId)
        {
            if (CategoryId.HasValue)
            {
                var cat = dataContext.context.categoria.First(x => x.id == CategoryId);
                this.CategoryId = Convert.ToInt32(cat.id);
                this.Nombre = cat.nombre;
                this.Descripcion = cat.descripcion;
            }
        }
    }
}