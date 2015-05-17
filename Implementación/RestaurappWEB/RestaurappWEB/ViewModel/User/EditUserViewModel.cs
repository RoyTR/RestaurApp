using RestaurappWEB.Controllers;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace RestaurappWEB.ViewModel.User
{
    public class EditUserViewModel
    {
        public Int32? UserId { get; set; }

        [Display(Name = "Nombre")]
        [Required(ErrorMessageResourceName = "CampoRequerido", ErrorMessageResourceType = typeof(i18n.ValidationStrings))]
        public String Nombre { get; set; }

        [Display(Name = "Apellidos")]
        [Required(ErrorMessageResourceName = "CampoRequerido", ErrorMessageResourceType = typeof(i18n.ValidationStrings))]
        public String Apellidos { get; set; }

        [Display(Name = "Usuario")]
        [Required(ErrorMessageResourceName = "CampoRequerido", ErrorMessageResourceType = typeof(i18n.ValidationStrings))]
        public String Usuario { get; set; }

        [Display(Name = "Email")]
        [Required(ErrorMessageResourceName = "CampoRequerido", ErrorMessageResourceType = typeof(i18n.ValidationStrings))]
        public String Email { get; set; }

        [Display(Name = "Contraseña")]
        [Required(ErrorMessageResourceName = "CampoRequerido", ErrorMessageResourceType = typeof(i18n.ValidationStrings))]
        public String Password { get; set; }

        public EditUserViewModel()
        {
        }

        public void CargarDatos(CargarDatosContext dataContext, Int32? UserId)
        {
            if (UserId.HasValue)
            {
                var usu = dataContext.context.usuario.First(x => x.id == UserId);
                this.UserId = Convert.ToInt32(usu.id);
                this.Nombre = usu.nombres;
                this.Apellidos = usu.apellidos;
                this.Email = usu.email;
                this.Password = usu.password;
                this.Usuario = usu.username;
            }
        }
    }
}