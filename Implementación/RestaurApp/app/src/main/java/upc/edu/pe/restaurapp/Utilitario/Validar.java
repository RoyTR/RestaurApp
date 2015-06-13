package upc.edu.pe.restaurapp.Utilitario;

import android.text.TextUtils;

import com.andreabaccega.widget.FormEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.validation.Validator;

/**
 * Created by Roy on 13/06/2015.
 */
public class Validar {

    public static boolean validarEditTexts(FormEditText[] fieldsToValidate) {
        boolean allValid = true;
        for (FormEditText field: fieldsToValidate) {
            allValid = field.testValidity() && allValid;
        }
        return allValid;
    }
}
