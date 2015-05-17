using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace RestaurappWEB.Helpers
{
    public static class ValidationHelpers
    {
        public static JsonResult JsonValidation(this ModelStateDictionary state)
        {
            return new JsonResult
            {
                Data = new
                {
                    Tag = "ValidationError",
                    State = from e in state
                            where e.Value.Errors.Count > 0
                            select new
                            {
                                Name = e.Key,
                                Error = string.Concat(e.Value.Errors.Select(x => x.ErrorMessage)
                                                                    .Concat(e.Value.Errors.Where(x => x.Exception != null).Select(x => x.Exception.Message)))
                            }
                }
            };
        }
    }
}