package main;

import static spark.Spark.get;
import static spark.Spark.post;

import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import controllers.HomeController;
import controllers.SessionController;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {
	
	public static void main(String[] args) {
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
	}
}
