package controllers;

import model.indicador.ConstructorDeIndicador;
import model.indicador.ConstructorDeIndicador.ExcepciónDeFórmulaInválida;
import model.indicador.Indicador;
import model.repositorios.RepositorioDeIndicadores;
import model.repositorios.Repositorios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;

public class IndicadorController {

    private RepositorioDeIndicadores repositorio = Repositorios.obtenerRepositorioDeIndicadores();

    public ModelAndView show(Request request, Response response) {

        HashMap<String, Object> map = new HashMap<>();

        List<Indicador> indicadores = repositorio.todos();

        map.put("indicadores", indicadores);

        return new ModelAndView(map, "indicadores.hbs");
    }

    public List<Indicador> all(Request req, Response resp)  {
        resp.status(200);
        return repositorio.todos();
    }

    public Indicador getIndicador(Request req, Response resp)   {
        String nombreIndicador = req.params("nombre");
        Indicador indicador = repositorio.encontrar(nombreIndicador);
        if(indicador != null) {
            resp.status(200);
            return indicador;
        }
        else resp.status(404);
        return null;
    }

    public Response crear(Request request, Response response)  {
        String nombre = request.queryParams("nombre");
        String descripcion = request.queryParams("descripcion");
        String formula = request.queryParams("formula");

        ConstructorDeIndicador constructorDeIndicador = new ConstructorDeIndicador();
        constructorDeIndicador.establecerNombre(nombre);
        constructorDeIndicador.establecerDescripción(descripcion);
        constructorDeIndicador.establecerFórmula(formula);

        if(repositorio.existeIndicador(nombre))
            response.status(202);
        else    {
            try {
                Indicador nuevoIndicador = constructorDeIndicador.construir();
                repositorio.agregar(nuevoIndicador);
                response.status(201);
            } catch(ExcepciónDeFórmulaInválida excepcion) {
                excepcion.printStackTrace();
                response.status(500);
            }
        }

        return response;
    }

    public Response borrar(Request request, Response response)  {
        String nombreIndicador = request.params("nombre");
        Indicador indicadorBorrado = repositorio.encontrar(nombreIndicador);
        if(indicadorBorrado != null) {
            repositorio.remover(indicadorBorrado);
            response.status(200);
        }
        else response.status(204);
        return response;
    }

    public Response modificar(Request request, Response response)   {
        String nombreViejo = request.queryParams("nombreViejo");
        String nombreNuevo = request.queryParams("nombre");
        String descripcion = request.queryParams("descripcion");
        System.out.println(nombreNuevo);
        System.out.println(nombreViejo);
        String formula = request.queryParams("formula");

        if(repositorio.existeIndicador(nombreViejo)) {
            Indicador indicadorViejo = repositorio.encontrar(nombreViejo);

            ConstructorDeIndicador constructorDeIndicador = new ConstructorDeIndicador();
            constructorDeIndicador.establecerNombre(nombreNuevo);
            constructorDeIndicador.establecerDescripción(descripcion);
            constructorDeIndicador.establecerFórmula(formula);

            try {
                Indicador nuevoIndicador = constructorDeIndicador.construir();
                repositorio.reemplazar(indicadorViejo, nuevoIndicador);
                response.status(200);
            } catch(ExcepciónDeFórmulaInválida excepcion) {
                excepcion.printStackTrace();
                response.status(500);
            }
        }
        else    response.status(204);

        return response;
    }
}
