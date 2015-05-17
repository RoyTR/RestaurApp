using PagedList.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace RestaurappWEB.Helpers
{
    public static class ConstantHelpers
    {
        public static readonly Int32 DEFAULT_PAGE_SIZE = 10;

        public const String ROL_ADMINISTRADOR = "ADM";
        public const String ROL_USUARIO = "USU";

        public const String ESTADO_ACTIVO = "ACT";
        public const String ESTADO_INACTIVO = "INA";

        public static class Layout
        {
            public static readonly String MODAL_LAYOUT_PATH = "~/Views/Shared/_ModalLayout.cshtml";
            public static readonly String MODAL_EMAIL_PATH = "~/Views/Shared/_MailLayout.cshtml";
        }

        public static PagedListRenderOptions Bootstrap3Pager
        {
            get
            {
                return new PagedListRenderOptions
                {
                    DisplayLinkToFirstPage = PagedListDisplayMode.IfNeeded,
                    DisplayLinkToLastPage = PagedListDisplayMode.IfNeeded,
                    DisplayLinkToPreviousPage = PagedListDisplayMode.IfNeeded,
                    DisplayLinkToNextPage = PagedListDisplayMode.IfNeeded,
                    DisplayLinkToIndividualPages = true,
                    DisplayPageCountAndCurrentLocation = false,
                    MaximumPageNumbersToDisplay = 10,
                    DisplayEllipsesWhenNotShowingAllPageNumbers = true,
                    EllipsesFormat = "&#8230;",
                    LinkToFirstPageFormat = "««",
                    LinkToPreviousPageFormat = "«",
                    LinkToIndividualPageFormat = "{0}",
                    LinkToNextPageFormat = "»",
                    LinkToLastPageFormat = "»»",
                    PageCountAndCurrentLocationFormat = "Page {0} of {1}.",
                    ItemSliceAndTotalFormat = "Showing items {0} through {1} of {2}.",
                    FunctionToDisplayEachPageNumber = null,
                    ClassToApplyToFirstListItemInPager = null,
                    ClassToApplyToLastListItemInPager = null,
                    ContainerDivClasses = new[] { "pagination-container" },
                    UlElementClasses = new[] { "pagination" },
                    LiElementClasses = Enumerable.Empty<string>(),
                };
            }
        }
    }
}