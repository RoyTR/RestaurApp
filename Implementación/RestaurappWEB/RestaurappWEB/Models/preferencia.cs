//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace RestaurappWEB.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class preferencia
    {
        public long id { get; set; }
        public long usuario_id { get; set; }
        public long categoria_id { get; set; }
    
        public virtual categoria categoria { get; set; }
        public virtual usuario usuario { get; set; }
    }
}
