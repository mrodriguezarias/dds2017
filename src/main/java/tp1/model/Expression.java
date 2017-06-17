package tp1.model;

@FunctionalInterface
public interface Expression {
	double eval(Company company, short period);
}
