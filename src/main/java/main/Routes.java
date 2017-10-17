package main;

import static spark.Spark.get;
import static spark.Spark.post;

import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import controllers.HomeController;
import controllers.CuentaController;
import controllers.IndicadorController;
import controllers.SessionController;
import spark.template.handlebars.HandlebarsTemplateEngine;

import com.google.gson.Gson;

public class Routes	{
	
	public static void main(String[] args) {
		Gson gson = new Gson();

		System.out.println("Iniciando servidor");

		HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
		
		port(8080);
		staticFileLocation("/public");
		
		HomeController home = new HomeController();
		get("/", home::show, engine);
		
		SessionController session = new SessionController();
		get("/login", session::showLogin, engine);
		post("/login", session::validateLogin);
		get("/logout", session::logout);

		CuentaController cuentas = new CuentaController();
		get("/cuentas", cuentas::getAll, engine);

		get("/empresas/:nombre/cuentas", cuentas::getCuentasByNombreDeEmpresaAndPeriodo, gson::toJson);

		IndicadorController indicadores = new IndicadorController();
		get("/indicadores", indicadores::show, engine);
	}
}
