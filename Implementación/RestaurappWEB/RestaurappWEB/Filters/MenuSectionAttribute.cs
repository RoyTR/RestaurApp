using System;
using System.Web.Mvc;

namespace RestaurappWEB.Filters
{
    public class MenuSectionAttribute : ActionFilterAttribute
    {
        private readonly String[] _sections;

        public MenuSectionAttribute(params String[] sections)
        {
            _sections = sections;
        }

        public override void OnActionExecuting(ActionExecutingContext filterContext)
        {
            filterContext.Controller.ViewBag.MenuSections = _sections;
            base.OnActionExecuting(filterContext);
        }
    }
}