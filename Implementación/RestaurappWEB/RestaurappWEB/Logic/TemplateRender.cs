using System;
using System.Collections.Generic;
using System.Dynamic;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace RestaurappWEB.Logic
{
    public class TemplateRender
    {
        ControllerBase controller;

        public TemplateRender(ControllerBase controller)
        {
            this.controller = controller;
        }

        public String Render(String template, object model)
        {
            IDictionary<string, object> anonymousDictionary = HtmlHelper.AnonymousObjectToHtmlAttributes(model);
            IDictionary<string, object> expando = new ExpandoObject();
            foreach (var item in anonymousDictionary)
                expando.Add(item);

            controller.ViewData.Model = (ExpandoObject)expando;

            using (StringWriter sw = new StringWriter())
            {
                var viewPath = "~/Templates/" + template + ".cshtml";
                var razorView = new RazorView(controller.ControllerContext, viewPath, "", false,new List<String>());
                ViewContext viewContext = new ViewContext(controller.ControllerContext, razorView, controller.ViewData, controller.TempData, sw);
                viewContext.View.Render(viewContext, sw);
                return sw.GetStringBuilder().ToString();
            }
        }
    }
}