package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import model.Cuenta;
import model.CuentaDeEmpresa;
import model.repositorios.RepositorioDeEmpresas;
import model.repositorios.Repositorios;
import model.Empresa;
import org.eclipse.ui.internal.Model;
import org.mozilla.javascript.json.JsonParser;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CuentaController {

    public ModelAndView getAll(Request request, Response response) {
        HashMap<String, Object> map = new HashMap<>();
        List<Empresa> empresas = Repositorios.obtenerRepositorioDeEmpresas().todos();

        List<Short> periodos = Repositorios.obtenerRepositorioDeEmpresas().getAllPeriodos();

        map.put("empresas", empresas);
        map.put("periodos", periodos);

        return new ModelAndView(map, "cuentas.hbs");
    }

    public List<CuentaDeEmpresa> getCuentasByNombreDeEmpresaAndPeriodo(Request request, Response response) {
        String nombreEmpresa = request.params("nombre");
        Short periodo = Short.valueOf(request.queryParams("periodo"));

        List<CuentaDeEmpresa> cuentas = Repositorios.obtenerRepositorioDeEmpresas().encontrar(nombreEmpresa).obtenerCuentas(periodo);

        return cuentas;
    }
}