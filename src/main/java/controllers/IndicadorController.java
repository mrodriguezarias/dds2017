package controllers;

import model.indicador.Indicador;
import model.repositorios.Repositorios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class IndicadorController {

    public ModelAndView show(Request request, Response response) {

        HashMap<String, Object> map = new HashMap<>();

        List<Indicador> indicadores = Repositorios.obtenerRepositorioDeIndicadores().todos();

        map.put("indicadores", indicadores);

        return new ModelAndView(map, "indicadores.hbs");
    }
}
